package com.accountbook.service;

import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.ecoEvent.EcoEventRepository;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.EcoEvent.EcoEventStaticsDto;
import com.accountbook.dto.EcoEvent.EcoEventStaticsResponse;
import com.accountbook.dto.category.CategoryListDto;
import com.accountbook.dto.user.UserDto;
import com.accountbook.exception.ecoEvent.EcoEventException;
import com.accountbook.exception.ecoEvent.EcoEventExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService {
    private final EcoEventService ecoEventService;
    private final UserService userService;

    private final CustomSettingRepository customSettingRepository;
    private final CategoryRepository categoryRepository;
    private final EcoEventRepository ecoEventRepository;
    private final UserRepository userRepository;


    /**
     * 사용자 통계 메서드
     */
    public void getUserStatistics () {
        var nowDate = LocalDateTime.now();
        var nowYear = nowDate.getYear();
        var nowMonth = nowDate.getMonth();
        var nowDay = nowDate.getDayOfMonth();

        try {
            // Get user list
            var users = userRepository.findAll();
            for (var user : users) {
                // Get user init day
                int initDay = user.getSetting().getInitDay();

                // 초기화 날짜 check
                if (initDay == nowDay) {

                    // make start date
                    //LocalDateTime startDate = LocalDateTime.of(nowYear, nowMonth.minus(1), initDay, 0, 0, 0);
                    //yyyy-MM-dd 형식 String
                    String startDate = Integer.toString(nowYear)+"-"+nowMonth.minus(1)+"-"+initDay;

                    // make end date
                    //LocalDateTime endDate = startDate.plusMonths(1);
                    String endDate = Integer.toString(nowYear)+"-"+nowMonth+"-"+initDay;

                    // Get expenditure ecoEvent
                    EcoEventReadRequest expenditureEcoEventRequest = new EcoEventReadRequest(user.getId(), EventType.EXPENDITURE, startDate, endDate);
                    List<EcoEventDto> expenditureEcoEvent = ecoEventService.getAllEcoEventByEventTypeAndUseDate(expenditureEcoEventRequest);

                    // Get income ecoEvent
                    EcoEventReadRequest incomeEcoEventRequest = new EcoEventReadRequest(user.getId(), EventType.INCOME, startDate, endDate);
                    List<EcoEventDto> incomeEcoEvnet = ecoEventService.getAllEcoEventByEventTypeAndUseDate(incomeEcoEventRequest);

                    // 지난 달 수입, 지출 금액
                    Long prevIncome = 0L;
                    Long prevExpenditure = 0L;

                    for (var event : expenditureEcoEvent) {
                        prevExpenditure += event.getAmount();
                    }

                    for (var event : incomeEcoEvnet) {
                        prevIncome += event.getAmount();
                    }

                    user.updatePrevInfo(prevIncome, prevExpenditure);
                    userRepository.addUser(user);
                    log.info(" >> Succes {} update statistics scheduler.", user.toString());
                }
            }
        } catch (Exception e) {
            log.info(" >> Failed to user statistics scheduler: {}", e);
        }
    }
    //이벤트 통계 정보 조회
    public EcoEventStaticsResponse summarizeEcoEvents(EcoEventReadRequest ecoEventReadRequest) throws Exception {
        String userId = ecoEventReadRequest.getUserId();
        CustomSetting customSetting = customSettingRepository.findById(userId).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CUSTOMSETTING));

        //s : 날짜 포맷팅
        List<Integer> formatDate = Arrays.stream(ecoEventReadRequest.getStartDate().split("-"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        if(formatDate == null){
            throw new EcoEventException(EcoEventExceptionCode.ERROR_PARSING_DATE);
        }
        LocalDateTime startDate = LocalDateTime.of(formatDate.get(0), formatDate.get(1),formatDate.get(2), 0,0,0);

        LocalDateTime endDate = null;
        if(ecoEventReadRequest.getEndDate() == null || ecoEventReadRequest.getStartDate().equals(ecoEventReadRequest.getEndDate())) {
            endDate = LocalDateTime.of(formatDate.get(0), formatDate.get(1),formatDate.get(2), 23,59,59);
        } else {
            formatDate = Arrays.stream(ecoEventReadRequest.getEndDate().split("-"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            if(formatDate == null){
                throw new EcoEventException(EcoEventExceptionCode.ERROR_PARSING_DATE);
            }
            endDate = LocalDateTime.of(formatDate.get(0), formatDate.get(1),formatDate.get(2), 0,0,0);
        }
        //e : 날짜 포맷팅

        if(customSetting.getInitDay() < endDate.getDayOfMonth()){
            // ex. 기준 일자가 오늘 일자보다 작은 경우 (기준일 1일, 오늘 3일 -> 당월 1일부터 당월 오늘까지)
            startDate = startDate.plusDays(-(endDate.getDayOfMonth() - customSetting.getInitDay()));
        } else {
            // ex. 기준 일자가 오늘 일자보다 큰 경우 (기준일 25일, 오늘 3일 -> 전월 25일부터 당월 오늘까지)
            startDate = LocalDateTime.of(startDate.getYear(), startDate.plusMonths(-1).getMonthValue(), customSetting.getInitDay(), startDate.getHour(), startDate.getMinute());
        }

        EcoEventStaticsResponse ecoEventStaticsResponse = new EcoEventStaticsResponse();

        //이번 달 가장 많은 지출 카테고리 정보 조회
        List<CategoryListDto> categoryDtoList = getTopCatergoryInfos(userId,startDate, endDate);
        ecoEventStaticsResponse.setCategoryDtoList(categoryDtoList);

        //지난 달 대비 이번 달 지출 금액
        List <String> moMExpenditureInfos = getMoMExpenditureInfos(userId,startDate,endDate);
        ecoEventStaticsResponse.setMoMExpenditureInfos(moMExpenditureInfos);

        // 이번 달 수입 대비 지출 금액 (지출/ 수입)
        List <String> thisMonthExpenditureInfos = getThisMonthExpenditureInfos(userId,startDate,endDate);
        ecoEventStaticsResponse.setThisMonthExpenditureInfos(thisMonthExpenditureInfos);

        // 이번달 수입, 지출 비율
        List <String> thisMonthExpenditureInfoPercent = getSumThisMonthExpenditureInfos(userId,startDate, endDate);
        ecoEventStaticsResponse.setThisMonthExpenditureInfoPercent(thisMonthExpenditureInfoPercent);

        //이번 달 시간대별 지출 금액
        Map<Integer,Long> inTimeExpenseAmountMap = getInTimeExpenseAmountInfos(userId, startDate, endDate);
        ecoEventStaticsResponse.setInTimeExpenseAmountMap(inTimeExpenseAmountMap);

        return ecoEventStaticsResponse;
    }

    //이번 달 가장 많은 지출 카테고리 정보 조회
    public List<CategoryListDto> getTopCatergoryInfos(String userId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        List <Long> maxCategorySeqList = new ArrayList<>();
        List<CategoryListDto> maxCategoryList = new ArrayList<>();
        Map<Long, Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId, startDate, endDate, EventType.EXPENDITURE)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getCategory().getSeq(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getCategorySeq, summingLong(EcoEventStaticsDto::getSumAmount)));

        try {
            Long maxValue = Collections.max(map.values());
            for (Map.Entry<Long, Long> m : map.entrySet()) {
                if (m.getValue() == maxValue) {
                    maxCategorySeqList.add(m.getKey());
                }
            }
        } catch(NoSuchElementException e){
            return maxCategoryList;
        }

        maxCategoryList = maxCategorySeqList.stream()
                .map(seq -> categoryRepository.findBySeq(seq))
                .map(category -> new CategoryListDto(category.get()))
                .collect(toList());

        return maxCategoryList;
    }

    //지난 달 대비 이번 달 지출 금액
    // [0] 금액
    // [1] 이번 달 오늘까지 지출 / 지난 달 총 지출 퍼센트
    public List<String> getMoMExpenditureInfos(String userId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        List<String> result = new ArrayList<>();

        Map<EventType, Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId, startDate, endDate, null)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getEventType, summingLong(EcoEventStaticsDto::getSumAmount)));

        Long thisExpenditure = map.get(EventType.EXPENDITURE);

        UserDto user = userService.getUser(userId);
        Long prevExpenditure = user.getPrevExpenditure();

        String amount = Long.toString(prevExpenditure - thisExpenditure);
        String percent = "0";
        if(Double.valueOf(prevExpenditure) > 0){
            percent = String.valueOf((Double.valueOf(thisExpenditure) / Double.valueOf(prevExpenditure)) * 100);
        }
        result.add(amount);
        result.add(percent);
        log.info("getMoMExpenditureInfos >> prevExpenditure : "+prevExpenditure+" | thisExpenditure : "+thisExpenditure);

        return result;
    }

    // 이번 달 수입 대비 지출 금액 (지출/ 수입)
    // [0] 지출 금액
    // [1] (지출/ 수입) 퍼센트
    public List<String> getThisMonthExpenditureInfos(String userId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        List<String> result = new ArrayList<>();

        Map<EventType, Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId, startDate, endDate, null)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getEventType, summingLong(EcoEventStaticsDto::getSumAmount)));

        String expenditure = Long.toString(map.get(EventType.EXPENDITURE));

        result.add(expenditure);

        if (map.get(EventType.INCOME) == null || map.get(EventType.INCOME) == 0) {
            result.add("0");
            return result;
        }

        Double resultPercent = Double.valueOf(map.get(EventType.EXPENDITURE)) / Double.valueOf(map.get(EventType.INCOME)) * 100;
        result.add(String.valueOf(Math.floor(resultPercent)));

        log.info("getThisMonthExpenditureInfos >> expenditure : "+expenditure+" | income : "+map.get(EventType.INCOME)+"| resultPercent : "+resultPercent);

        return result;
    }

    // 이번달 수입, 지출 비율
    // [0] 지출/(수입 + 지출) 퍼센트
    // [1] 수입/(수입 + 지출) 퍼센트
    public List<String> getSumThisMonthExpenditureInfos(String userId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        List<String> result = new ArrayList<>();

        Map<EventType, Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId, startDate, endDate, null)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getEventType, summingLong(EcoEventStaticsDto::getSumAmount)));

        String expenditure = Long.toString(map.get(EventType.EXPENDITURE));

        if (map.get(EventType.EXPENDITURE) == null || map.get(EventType.EXPENDITURE) == 0) {
            result.add("0");
            result.add("100");
        } else if (map.get(EventType.INCOME) == null || map.get(EventType.INCOME) == 0) {
            result.add("100");
            result.add("0");
        } else {
            Double sum = Double.valueOf(map.get(EventType.EXPENDITURE)) + Double.valueOf(map.get(EventType.INCOME));
            Double expenditurePercent = (Double.valueOf(map.get(EventType.EXPENDITURE)) / sum) * 100;
            Double incomePercent = (Double.valueOf(map.get(EventType.INCOME)) / sum) * 100;

            result.add(String.valueOf(Math.floor(expenditurePercent)));
            result.add(String.valueOf(Math.floor(incomePercent)));
        }
        log.info("getSumThisMonthExpenditureInfos >> expenditure : "+map.get(EventType.EXPENDITURE)+" | income : "+map.get(EventType.INCOME));

        return result;
    }
    //이번 달 시간대별 지출 금액
    public Map<Integer,Long> getInTimeExpenseAmountInfos(String userId, LocalDateTime startDate, LocalDateTime endDate) throws Exception{
        Map<Integer,Long> map = new HashMap<>();
        map = ecoEventRepository.findByEventTypeAndUseDate(userId, startDate, endDate, EventType.EXPENDITURE)
                                .parallelStream()
                                .map(e -> new EcoEventStaticsDto(e.getUseDate().getHour(), e.getAmount()))
                                .collect(groupingBy(EcoEventStaticsDto::getTime, summingLong(EcoEventStaticsDto::getSumAmount)));

        return map;
    }
}

package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.ecoEvent.EcoEventRepository;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.*;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.user.UserDto;
import com.accountbook.exception.ecoEvent.EcoEventException;
import com.accountbook.exception.ecoEvent.EcoEventExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EcoEventService {
    private final EcoEventRepository ecoEventRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final CustomSettingRepository customSettingRepository;

    private final UserService userService;

    //이벤트 전체 조회
    @Transactional(readOnly = true)
    public List<EcoEventDto> getAllEcoEvent(){
        return ecoEventRepository.findAll().stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 상세 조회
    @Transactional(readOnly = true)
    public EcoEventDto getOneEcoEvent(Long ecoEventSeq) throws Exception{
        return new EcoEventDto(ecoEventRepository.findBySeq(ecoEventSeq).orElseThrow(() -> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_ECOEVENT)));
    }

    //이벤트 조회 by User
    @Transactional(readOnly = true)
    public List<EcoEventDto> getEcoEventByUser(String userId){
        return ecoEventRepository.findByUserId(userId).stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 조회 by User, EventType, UseDate
    @Transactional(readOnly = true)
    public List<EcoEventDto> getAllEcoEvnetByEventTypeAndUseDate(EcoEventReadRequest ecoEventReadRequest) {
        String userId = ecoEventReadRequest.getUserId();
        LocalDateTime startDate = ecoEventReadRequest.getStartDate();
        LocalDateTime endDate = ecoEventReadRequest.getEndDate();
        EventType eventType = ecoEventReadRequest.getEventType();

        return ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,endDate,eventType)
                                 .stream()
                                 .map(EcoEventDto::new)
                                 .collect(Collectors.toList());
    }

    //이벤트 등록
    public Long enrollEcoEvents(EcoEventRequest ecoEventRequest) throws Exception{
        Category category = categoryRepository.findBySeq(ecoEventRequest.getCategorySeq()).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CATEGORY));
        User user = userRepository.findById(ecoEventRequest.getUserId()).orElseThrow(() -> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_USER));

        EcoEvent ecoEvent = EcoEvent.createEcoEvent(ecoEventRequest, user, category);
        ecoEventRepository.save(ecoEvent);

        return ecoEvent.getSeq();
    }

    //이벤트 수정
    public EcoEventDto updateEcoEvents(EcoEventRequest ecoEventRequest, Long ecoEventSeq) throws Exception{
        Category category = categoryRepository.findBySeq(ecoEventRequest.getCategorySeq()).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CATEGORY));
        EcoEvent ecoEvent = ecoEventRepository.findBySeq(ecoEventSeq).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_ECOEVENT));

        ecoEvent.changeEcoEvent(ecoEventRequest,category);

        ecoEventRepository.flush();

        return new EcoEventDto(ecoEventRepository.findBySeq(ecoEventSeq).get());
    }

    //이벤트 삭제
    public Boolean deleteEcoEvents(Long EventsSeq) throws Exception{
        try {
            ecoEventRepository.deleteById(EventsSeq);
        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return false;
        }
        try{
            ecoEventRepository.findBySeq(EventsSeq).orElseThrow(() -> new NoSuchElementException());
        }catch (NoSuchElementException e){
            return true;
        }
        return false;
    }
    //이벤트 통계 정보 조회
    public EcoEventStaticsResponse summarizeEcoEvents(EcoEventReadRequest ecoEventReadRequest) throws Exception {
        String userId = ecoEventReadRequest.getUserId();
        CustomSetting customSetting = customSettingRepository.findById(userId).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CUSTOMSETTING));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        if(customSetting.getInitDay() < endDate.getDayOfMonth()){
            // ex. 기준 일자가 오늘 일자보다 작은 경우 (기준일 1일, 오늘 3일 -> 당월 1일부터 당월 오늘까지)
            startDate = startDate.plusDays(-(endDate.getDayOfMonth() - customSetting.getInitDay()));
        } else {
            // ex. 기준 일자가 오늘 일자보다 큰 경우 (기준일 25일, 오늘 3일 -> 전월 25일부터 당월 오늘까지)
            startDate = LocalDateTime.of(startDate.getYear(), startDate.plusMonths(-1).getMonthValue(), customSetting.getInitDay(), startDate.getHour(), startDate.getMinute());
        }

        EcoEventStaticsResponse ecoEventStaticsResponse = new EcoEventStaticsResponse();

        //이번 달 가장 많은 지출 카테고리 정보 조회
        List<CategoryDto> categoryDtoList = getTopCatergoryInfos(userId,startDate);
        ecoEventStaticsResponse.setCategoryDtoList(categoryDtoList);

        //지난 달 대비 이번 달 지출 금액
        List <String> moMExpenditureInfos = getMoMExpenditureInfos(userId,startDate);
        ecoEventStaticsResponse.setMoMExpenditureInfos(moMExpenditureInfos);

        // 이번 달 수입 대비 지출 금액 (지출/ 수입)
        List <String> thisMonthExpenditureInfos = getSumThisMonthExpenditureInfos(userId,startDate);
        ecoEventStaticsResponse.setThisMonthExpenditureInfos(thisMonthExpenditureInfos);

        //이번 달 시간대별 지출 금액
        Map<Integer,Long> inTimeExpenseAmountMap = getInTimeExpenseAmountInfos(userId, startDate);
        ecoEventStaticsResponse.setInTimeExpenseAmountMap(inTimeExpenseAmountMap);

        return ecoEventStaticsResponse;
    }

    //이번 달 가장 많은 지출 카테고리 정보 조회
    public List<CategoryDto> getTopCatergoryInfos(String userId, LocalDateTime startDate){
        List <Long> maxCategorySeqList = new ArrayList<>();
        Map<Long,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),EventType.EXPENDITURE)
                                               .parallelStream()
                                               .map(e -> new EcoEventStaticsDto(e.getCategory().getSeq(), e.getAmount()))
                                               .collect(groupingBy(EcoEventStaticsDto::getCategorySeq,summingLong(EcoEventStaticsDto::getSumAmount)));

        Long maxValue = Collections.max(map.values());
        for(Map.Entry<Long, Long> m : map.entrySet()) {
            if(m.getValue() == maxValue) {
                maxCategorySeqList.add(m.getKey());
            }
        }
        List <CategoryDto> maxCategoryList = new ArrayList<>();
        if(maxCategorySeqList != null && !maxCategorySeqList.isEmpty()){
            maxCategoryList = maxCategorySeqList.stream()
                                                .map(seq -> categoryRepository.findBySeq(seq))
                                                .map(category -> new CategoryDto(category.get()))
                                                .collect(toList());
        }

        if(maxCategoryList != null && !maxCategoryList.isEmpty()){
            return maxCategoryList;
        }
        return maxCategoryList;
    }

    //지난 달 대비 이번 달 지출 금액
    // [0] 금액
    // [1] 이번 달 오늘까지 지출 / 지난 달 총 지출 퍼센트
    public List<String> getMoMExpenditureInfos(String userId, LocalDateTime startDate) throws Exception {
        Map<EventType,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),null)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getEventType,summingLong(EcoEventStaticsDto::getSumAmount)));

        Long thisExpenditure = map.get(EventType.EXPENDITURE);

        UserDto user = userService.getUser(userId);
        Long prevExpenditure = user.getPrevExpenditure();

        List<String> result = new ArrayList<>();
        result.add(Long.toString(prevExpenditure - thisExpenditure));
        result.add(String.valueOf((Double.valueOf(thisExpenditure) / Double.valueOf(prevExpenditure)) * 100));
        return result;
    }

    // 이번 달 수입 대비 지출 금액 (지출/ 수입)
    // [0] 지출 금액
    // [1] (지출/ 수입) 퍼센트
    public List<String> getThisMonthExpenditureInfos(String userId, LocalDateTime startDate){
        Map<EventType,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),null)
                                                    .parallelStream()
                                                    .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                                                    .collect(groupingBy(EcoEventStaticsDto::getEventType,summingLong(EcoEventStaticsDto::getSumAmount)));

        String expenditure = Long.toString(map.get(EventType.EXPENDITURE));
        List<String> result = new ArrayList<>();
        result.add(expenditure);

        if(map.get(EventType.INCOME) == null || map.get(EventType.INCOME) == 0){
            result.add("0");
            return result;
        }

        Double resultPercent = Double.valueOf(map.get(EventType.EXPENDITURE))/ Double.valueOf(map.get(EventType.INCOME)) * 100;
        result.add(String.valueOf(Math.floor(resultPercent)));
        return result;
    }

    // 이번달 수입, 지출 비율
    // [0] 지출/(수입 + 지출) 퍼센트
    // [1] 수입/(수입 + 지출) 퍼센트
    public List<String> getSumThisMonthExpenditureInfos(String userId, LocalDateTime startDate){

        Map<EventType,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),null)
                .parallelStream()
                .map(e -> new EcoEventStaticsDto(e.getEventType(), e.getAmount()))
                .collect(groupingBy(EcoEventStaticsDto::getEventType,summingLong(EcoEventStaticsDto::getSumAmount)));

        String expenditure = Long.toString(map.get(EventType.EXPENDITURE));
        List<String> result = new ArrayList<>();

        if(map.get(EventType.EXPENDITURE) == null || map.get(EventType.EXPENDITURE) == 0){
            result.add("0");
            result.add("100");
        } else if(map.get(EventType.INCOME) == null || map.get(EventType.INCOME) == 0){
            result.add("100");
            result.add("0");
        } else {
            Double sum = Double.valueOf(map.get(EventType.EXPENDITURE)) + Double.valueOf(map.get(EventType.INCOME));
            Double expenditurePercent = (Double.valueOf(map.get(EventType.EXPENDITURE)) / sum) * 100;
            Double incomePercent = (Double.valueOf(map.get(EventType.INCOME)) / sum) * 100;

            result.add(String.valueOf(Math.floor(expenditurePercent)));
            result.add(String.valueOf(Math.floor(incomePercent)));
        }
        return result;
    }
    //이번 달 시간대별 지출 금액
    public Map<Integer,Long> getInTimeExpenseAmountInfos(String userId, LocalDateTime startDate) throws Exception{
        Map<Integer,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),EventType.EXPENDITURE)
                                                  .parallelStream()
                                                  .map(e -> new EcoEventStaticsDto(e.getUseDate().getHour(), e.getAmount()))
                                                  .collect(groupingBy(EcoEventStaticsDto::getTime,summingLong(EcoEventStaticsDto::getSumAmount)));
        return map;
    }

}
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
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventInTimeResponse;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.exception.ecoEvent.EcoEventException;
import com.accountbook.exception.ecoEvent.EcoEventExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public void summarizeEcoEvents(EcoEventReadRequest ecoEventReadRequest) throws Exception {
        //getTopCatergoryInfos(ecoEventReadRequest);
        //getExpensesAndIncomeAmountInfos(ecoEventReadRequest);
        //getExpensesToIncomeAmountInfos(ecoEventReadRequest);
        Map<Integer,Long> inTimeExpenseAmountMap = getInTimeExpenseAmountInfos(ecoEventReadRequest);
    }

    //이번 달 가장 많은 지출 카테고리 정보 조회
    public void getTopCatergoryInfos(EcoEventReadRequest ecoEventReadRequest){

    }

    //지난 달 대비 이번 달 수입, 지출 금액
    public void getExpensesAndIncomeAmountInfos(EcoEventReadRequest ecoEventReadRequest){

    }

    // 이번 달 수입 대비 지출 금액
    public void getExpensesToIncomeAmountInfos(EcoEventReadRequest ecoEventReadRequest){

    }
    //이번 달 시간대별 지출 금액
    public Map<Integer,Long> getInTimeExpenseAmountInfos(EcoEventReadRequest ecoEventReadRequest) throws Exception{
        String userId = ecoEventReadRequest.getUserId();

        CustomSetting customSetting = customSettingRepository.findById(userId).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CUSTOMSETTING));

        LocalDateTime startDate = LocalDateTime.now(), endDate = LocalDateTime.now();
        if(customSetting.getInitDay() < endDate.getDayOfMonth()){
            startDate = startDate.plusDays(-(endDate.getDayOfMonth() - customSetting.getInitDay()));
        } else {
            startDate = LocalDateTime.of(startDate.getYear(), startDate.plusMonths(-1).getMonthValue(), customSetting.getInitDay(), startDate.getHour(), startDate.getMinute());
        }

        Map<Integer,Long> map = ecoEventRepository.findByEventTypeAndUseDate(userId,startDate,LocalDateTime.now(),EventType.EXPENDITURE)
                                                  .parallelStream()
                                                  .map(e -> new EcoEventInTimeResponse(e.getUseDate().getHour(), e.getAmount()))
                                                  .collect(groupingBy(EcoEventInTimeResponse::getTime,summingLong(EcoEventInTimeResponse::getSumAmount)));

        return map;
    }
}
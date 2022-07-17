package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.ecoEvent.EcoEventRepository;

import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.*;
import com.accountbook.exception.ecoEvent.EcoEventException;
import com.accountbook.exception.ecoEvent.EcoEventExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EcoEventService {
    private final EcoEventRepository ecoEventRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    //이벤트 전체 조회
    @Transactional(readOnly = true)
    public List<EcoEventDto> getAllEcoEvent() throws Exception{
        return ecoEventRepository.findAll().stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 상세 조회
    @Transactional(readOnly = true)
    public EcoEventDto getOneEcoEvent(Long ecoEventSeq) throws Exception{
        return new EcoEventDto(ecoEventRepository.findBySeq(ecoEventSeq).orElseThrow(() -> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_ECOEVENT)));
    }

    //이벤트 조회 by User
    @Transactional(readOnly = true)
    public List<EcoEventDto> getEcoEventByUser(String userId) throws Exception{
        return ecoEventRepository.findByUserId(userId).stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 조회 by User, EventType, UseDate
    @Transactional(readOnly = true)
    public List<EcoEventDto> getAllEcoEventByEventTypeAndUseDate(EcoEventReadRequest ecoEventReadRequest) throws Exception{
        String userId = ecoEventReadRequest.getUserId();

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
            endDate = LocalDateTime.of(formatDate.get(0), formatDate.get(1),formatDate.get(2), 23,59,59);;
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

        EcoEvent ecoEvent = ecoEventRepository.findBySeq(ecoEventSeq).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_ECOEVENT));

        Category category = null;
        if(ecoEventRequest.getCategorySeq() != null) {
            category = categoryRepository.findBySeq(ecoEventRequest.getCategorySeq()).orElseThrow(() -> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CATEGORY));
        }
        ecoEvent.changeEcoEvent(ecoEventRequest,category);

        ecoEventRepository.flush();

        return new EcoEventDto(ecoEventRepository.findBySeq(ecoEventSeq).get());
    }

    //이벤트 삭제
    public Boolean deleteEcoEvents(Long EventsSeq) {
        try {
            ecoEventRepository.deleteById(EventsSeq);
        }catch(EmptyResultDataAccessException e){
            throw new EcoEventException(EcoEventExceptionCode.NOT_FOUND_ECOEVENT);
        }

        return true;
    }
}
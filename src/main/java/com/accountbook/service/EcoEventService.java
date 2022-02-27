package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.ecoEvent.EcoEventRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.exception.ecoEvent.EcoEventException;
import com.accountbook.exception.ecoEvent.EcoEventExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EcoEventService {
    private final EcoEventRepository ecoEventRepository;

    private final CategoryRepository categoryRepository;

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

    @Transactional(readOnly = true)
    public List<EcoEventDto> getEcoEventByUser(String userId){
        return ecoEventRepository.findByUserId(userId).stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 등록
    public Long enrollEcoEvents(EcoEventRequest ecoEventRequest) throws Exception{
        Category category = categoryRepository.findBySeq(ecoEventRequest.getCategorySeq()).orElseThrow(()-> new EcoEventException(EcoEventExceptionCode.NOT_FOUND_CATEGORY));

        EcoEvent ecoEvent = EcoEvent.createEcoEvent(ecoEventRequest, category);
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
}
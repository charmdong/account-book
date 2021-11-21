package com.accountbook.service;

import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.repository.EcoEventRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EcoEventService {
    private final EcoEventRepository ecoEventRepository;

    //이벤트 전체 조회
    @Transactional(readOnly = true)
    public List<EcoEventDto> getAllEcoEvent(){
        return ecoEventRepository.findAll().stream().map(EcoEventDto::new).collect(Collectors.toList());
    }

    //이벤트 상세 조회
    @Transactional(readOnly = true)
    public EcoEventDto getOneEcoEvent(Long ecoEventSeq){
        return new EcoEventDto(ecoEventRepository.findBySeq(ecoEventSeq));
    }

    //이벤트 등록
    public void enrollEcoEvents(EcoEventRequest ecoEventRequest){
        EcoEvent ecoEvent = EcoEvent.createEcoEvent(ecoEventRequest);
        ecoEventRepository.save(ecoEvent);
    }

    //이벤트 수정
    public void updateEcoEvents(EcoEventRequest ecoEventRequest, Long ecoEventSeq){
        EcoEvent ecoEvent = ecoEventRepository.findBySeq(ecoEventSeq);
        if(ecoEvent == null){
            throw new IllegalStateException();
        }
        ecoEvent.changeEcoEvent(ecoEventRequest);
        ecoEventRepository.save(ecoEvent);
    }
    //이벤트 삭제
    public void deleteEcoEvents(Long EventsSeq){
        EcoEvent ecoEvent = ecoEventRepository.findBySeq(EventsSeq);
        if(ecoEvent == null){
            throw new IllegalStateException();
        }
        ecoEventRepository.remove(ecoEvent);
    }
}
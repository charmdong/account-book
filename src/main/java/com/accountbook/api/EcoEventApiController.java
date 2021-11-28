package com.accountbook.api;

import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.service.EcoEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/ecoevents")
@RequiredArgsConstructor
public class EcoEventApiController {
    private final EcoEventService ecoEventService;

    //금융 이벤트 전체 조회
    @GetMapping("/")
    public List<EcoEventDto> getAllBudget(){
        return ecoEventService.getAllEcoEvent();
    }

    //금융 이벤트 조회
    @GetMapping("/{eventSeq}")
    public EcoEventDto getOneBudget(@PathVariable long ecoEventSeq){
        return ecoEventService.getOneEcoEvent(ecoEventSeq);
    }

    //금융 이벤트 등록
    @PostMapping("/")
    public void create(@RequestBody @Valid EcoEventRequest ecoEventRequest){
        ecoEventService.enrollEcoEvents(ecoEventRequest);
    }

    //금융 이벤트 수정
    @PatchMapping("/{eventSeq}")
    public void update(@RequestBody @Valid EcoEventRequest ecoEventRequest, @PathVariable long ecoEventSeq){
        ecoEventService.updateEcoEvents(ecoEventRequest, ecoEventSeq);
    }

    //금융 이벤트 삭제
    @DeleteMapping("/{eventSeq}")
    public void delete(@PathVariable long ecoEventSeq){
        ecoEventService.deleteEcoEvents(ecoEventSeq);
    }
}


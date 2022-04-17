package com.accountbook.api;

import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.EcoEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ApiResponse getAllEcoEvent(){
        List<EcoEventDto> ecoEventDtoList = ecoEventService.getAllEcoEvent();
        ApiResponse apiResponse = new ApiResponse(ecoEventDtoList, HttpStatus.OK, CommonResponseMessage.SUCCESS);
        return apiResponse;
    }

    //금융 이벤트 조회
    @GetMapping("/{eventSeq}")
    public ApiResponse getOneEcoEvent(@PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.getOneEcoEvent(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 조건 조회 - user
    @GetMapping("/{userId}")
    public ApiResponse getAllEcoEvnetByUser(@PathVariable String userId) throws Exception {
        return new ApiResponse(ecoEventService.getEcoEventByUser(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 조건 조회 - user,eventType,useDate
    @GetMapping("/condition")
    public ApiResponse getAllEcoEventByEventTypeAndUseDate(@RequestBody @Valid EcoEventReadRequest ecoEventReadRequest) throws Exception {
        return new ApiResponse(ecoEventService.getAllEcoEvnetByEventTypeAndUseDate(ecoEventReadRequest), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 등록
    @PostMapping("/")
    public ApiResponse create(@RequestBody @Valid EcoEventRequest ecoEventRequest) throws Exception {
        return new ApiResponse(ecoEventService.enrollEcoEvents(ecoEventRequest), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 수정
    @PatchMapping("/{eventSeq}")
    public ApiResponse update(@RequestBody @Valid EcoEventRequest ecoEventRequest, @PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.updateEcoEvents(ecoEventRequest, ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 삭제
    @DeleteMapping("/{eventSeq}")
    public ApiResponse delete(@PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.deleteEcoEvents(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}


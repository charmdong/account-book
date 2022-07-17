package com.accountbook.api;

import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.EcoEventService;
import com.accountbook.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/ecoevents")
@RequiredArgsConstructor
public class EcoEventApiController {
    private final EcoEventService ecoEventService;
    private final StatisticsService statisticsService;

    //금융 이벤트 전체 조회
    @GetMapping("/")
    public ApiResponse getAllEcoEvent() throws Exception {
        ApiResponse apiResponse = new ApiResponse(ecoEventService.getAllEcoEvent(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
        return apiResponse;
    }

    //금융 이벤트 조회
    @GetMapping("/{ecoEventSeq}")
    public ApiResponse getOneEcoEvent(@PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.getOneEcoEvent(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 조건 조회 - user
    @GetMapping("/condition/{userId}")
    public ApiResponse getAllEcoEventByUser(@PathVariable String userId) throws Exception {
        return new ApiResponse(ecoEventService.getEcoEventByUser(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 조건 조회 - user,eventType,useDate
    @GetMapping("/condition")
    public ApiResponse getAllEcoEventByEventTypeAndUseDate(@RequestBody @Valid EcoEventReadRequest ecoEventReadRequest) throws Exception {
        return new ApiResponse(ecoEventService.getAllEcoEventByEventTypeAndUseDate(ecoEventReadRequest), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 등록
    @PostMapping("/")
    public ApiResponse create(@RequestBody @Valid EcoEventRequest ecoEventRequest) throws Exception {
        return new ApiResponse(ecoEventService.enrollEcoEvents(ecoEventRequest), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 수정
    @PatchMapping("/{ecoEventSeq}")
    public ApiResponse update(@RequestBody @Valid EcoEventRequest ecoEventRequest, @PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.updateEcoEvents(ecoEventRequest, ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 삭제
    @DeleteMapping("/{ecoEventSeq}")
    public ApiResponse delete(@PathVariable long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.deleteEcoEvents(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 통계
    @PostMapping("/summary")
    public ApiResponse summary(@RequestBody @Valid EcoEventReadRequest ecoEventReadRequest) throws Exception{
        return new ApiResponse(statisticsService.summarizeEcoEvents(ecoEventReadRequest),HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}


package com.accountbook.api;

import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.EcoEventService;
import com.accountbook.service.StatisticsService;
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
    private final StatisticsService statisticsService;

    //금융 이벤트 전체 조회
    @GetMapping("/")
    public ApiResponse getAllEcoEvent(){
        List<EcoEventDto> ecoEventDtoList = ecoEventService.getAllEcoEvent();
        ApiResponse apiResponse = new ApiResponse(ecoEventDtoList, HttpStatus.OK, CommonResponseMessage.SUCCESS);
        return apiResponse;
    }

    //금융 이벤트 조회
    @GetMapping("/detail")
    public ApiResponse getOneEcoEvent(@RequestParam long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.getOneEcoEvent(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 조건 조회 - user
    @GetMapping("/user")
    public ApiResponse getAllEcoEventByUser(@RequestParam String userId) throws Exception {
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
    @PatchMapping("/")
    public ApiResponse update(@RequestBody @Valid EcoEventRequest ecoEventRequest, @RequestParam long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.updateEcoEvents(ecoEventRequest, ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 삭제
    @DeleteMapping("/")
    public ApiResponse delete(@RequestParam long ecoEventSeq) throws Exception {
        return new ApiResponse(ecoEventService.deleteEcoEvents(ecoEventSeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //금융 이벤트 통계
    @PostMapping("/summary")
    public ApiResponse summary(@RequestBody @Valid EcoEventReadRequest ecoEventReadRequest) throws Exception{
        return new ApiResponse(statisticsService.summarizeEcoEvents(ecoEventReadRequest),HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}


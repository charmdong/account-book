package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class EcoEventReadRequest {
    @NotEmpty(message = "사용자 누락")
    private String userId;

    private EventType eventType; //타입 미 지정 시 전체 조회

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public EcoEventReadRequest(){} //테스트용

    public EcoEventReadRequest(String userId, EventType eventType,LocalDateTime startDate,LocalDateTime endDate) {
        this.userId = userId;
        this.eventType = eventType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

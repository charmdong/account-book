package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class EcoEventRequest {

    @NotEmpty(message = "사용자 누락")
    private String userId;

    //@NotEmpty(message = "이벤트 타입 누락")
    private EventType eventType;

    private LocalDateTime useDate;

    //@NotEmpty(message = "금액 누락")
    private Long amount;

    //@NotEmpty(message = "카테고리 누락")
    private Long categorySeq;

    public EcoEventRequest(String userId, EventType eventType, LocalDateTime useDate, Long amount, Long categorySeq) {
        this.userId = userId;
        this.eventType = eventType;
        this.useDate = useDate;
        this.amount = amount;
        this.categorySeq = categorySeq;
    }

}

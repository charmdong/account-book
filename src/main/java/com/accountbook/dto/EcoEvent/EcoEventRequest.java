package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcoEventRequest {

    //@NotEmpty(message = "사용자 누락")
    private String userId;

    //@NotEmpty(message = "이벤트 타입 누락")
    private EventType eventType;

    private LocalDateTime useDate;

    //@NotEmpty(message = "금액 누락")
    private Long amount;

    //금융 이벤트 설명
    private String desc;

    //@NotEmpty(message = "카테고리 누락")
    private Long categorySeq;
}

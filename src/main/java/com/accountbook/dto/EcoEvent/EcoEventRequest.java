package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcoEventRequest {

    private String userId;

    private EventType eventType;

    private LocalDateTime useDate;

    private Long amount;

    private String desc;

    private Long categorySeq;
}

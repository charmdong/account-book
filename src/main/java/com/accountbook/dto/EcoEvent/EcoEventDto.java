package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EcoEventDto {
    private Long seq;

    private String userId;

    private EventType eventType;

    private LocalDateTime useDate;

    private Long amount;

    private String desc;

    private Long categorySeq;

    public EcoEventDto(EcoEvent ecoEvent){
        this.seq = ecoEvent.getSeq();
        this.userId = ecoEvent.getUser().getId();
        this.eventType = ecoEvent.getEventType();
        this.useDate = ecoEvent.getUseDate();
        this.amount = ecoEvent.getAmount();
        this.desc = ecoEvent.getDescription();
        this.categorySeq = ecoEvent.getCategory().getSeq();
    }
}

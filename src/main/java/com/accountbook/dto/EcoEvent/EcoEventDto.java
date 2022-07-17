package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class EcoEventDto {
    private Long seq;

    private String userId;

    private EventType eventType;

    private String useDate;

    private Long amount;

    private String desc;

    private Long categorySeq;

    private String categoryName;

    public EcoEventDto(EcoEvent ecoEvent){
        this.seq = ecoEvent.getSeq();
        this.userId = ecoEvent.getUser().getId();
        this.eventType = ecoEvent.getEventType();
        this.useDate = ecoEvent.getUseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.amount = ecoEvent.getAmount();
        this.desc = ecoEvent.getDescription();
        this.categorySeq = ecoEvent.getCategory().getSeq();
        this.categoryName = ecoEvent.getCategory().getName();
    }
}

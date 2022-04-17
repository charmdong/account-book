package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EcoEventDto {
    private Long seq;

    private User user;

    private EventType eventType;

    private LocalDateTime useDate;

    private Long amount;

    private Category category;

    public EcoEventDto(EcoEvent ecoEvent){
        this.seq = ecoEvent.getSeq();
        this.eventType = ecoEvent.getEventType();
        this.useDate = ecoEvent.getUseDate();
        this.amount = ecoEvent.getAmount();
        this.category = ecoEvent.getCategory();

    }
}

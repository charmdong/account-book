package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.Data;
@Data
public class EcoEventStaticsDto {

    private int time;

    private Long sumAmount;

    private Long categorySeq;

    private EventType eventType;

    public EcoEventStaticsDto(int time, Long sumAmount) {
        this.time = time;
        this.sumAmount = sumAmount;
    }

    public EcoEventStaticsDto(Long categorySeq, Long sumAmount) {
        this.categorySeq = categorySeq;
        this.sumAmount = sumAmount;
    }

    public EcoEventStaticsDto(EventType eventType, Long sumAmount) {
        this.eventType = eventType;
        this.sumAmount = sumAmount;
    }
}

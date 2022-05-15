package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.enums.EventType;
import lombok.Data;
@Data
public class EcoEventStaticsResponse {

    private int time;

    private Long sumAmount;

    private Long categorySeq;

    private EventType eventType;

    public EcoEventStaticsResponse(int time, Long sumAmount) {
        this.time = time;
        this.sumAmount = sumAmount;
    }

    public EcoEventStaticsResponse(Long categorySeq, Long sumAmount) {
        this.categorySeq = categorySeq;
        this.sumAmount = sumAmount;
    }

    public EcoEventStaticsResponse(EventType eventType, Long sumAmount) {
        this.eventType = eventType;
        this.sumAmount = sumAmount;
    }
}

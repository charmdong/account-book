package com.accountbook.dto.EcoEvent;

import lombok.Data;
@Data
public class EcoEventInTimeResponse {

    private int time;

    private Long sumAmount;

    public EcoEventInTimeResponse(int time, Long sumAmount) {
        this.time = time;
        this.sumAmount = sumAmount;
    }
}

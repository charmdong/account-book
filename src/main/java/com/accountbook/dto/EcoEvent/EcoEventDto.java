package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EcoEventDto {
    private Long seq;

    private EventType eventType;

    private LocalDateTime useDate;

    private Long amount;

    private AssetType assetType;

    private Category category;

    public EcoEventDto(Long seq, EventType eventType, LocalDateTime useDate, Long amount, AssetType assetType, Category category) {
        this.seq = seq;
        this.eventType = eventType;
        this.useDate = useDate;
        this.amount = amount;
        this.assetType = assetType;
        this.category = category;
    }
    public EcoEventDto(EcoEvent ecoEvent){
        this.seq = ecoEvent.getSeq();
        this.eventType = ecoEvent.getEventType();
        this.useDate = ecoEvent.getUseDate();
        this.amount = ecoEvent.getAmount();
        this.assetType = ecoEvent.getAssetType();
        this.category = ecoEvent.getCategory();

    }
}

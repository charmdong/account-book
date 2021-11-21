package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.enums.EventType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class EcoEventRequest {

    @NotEmpty
    private EventType eventType;

    private LocalDateTime useDate;

    @NotEmpty
    private Long amount;

    @NotEmpty
    private AssetType assetType;

    @NotEmpty
    private Category category;

    public EcoEventRequest(EventType eventType, Long amount, AssetType assetType, Category category) {
        this.eventType = eventType;
        this.amount = amount;
        this.assetType = assetType;
        this.category = category;
    }
}

package com.accountbook.dto.EcoEvent;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.enums.EventType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class EcoEventRequest {

    @NotEmpty(message = "이벤트 타입 누락")
    private EventType eventType;

    private LocalDateTime useDate;

    @NotEmpty(message = "금액 누락")
    private Long amount;

    @NotEmpty(message = "자산 타입 누락")
    private AssetType assetType;

    @NotEmpty(message = "카테고리 누락")
    private Long categorySeq;

    public EcoEventRequest(EventType eventType, LocalDateTime useDate,Long amount, AssetType assetType, Long categorySeq) {
        this.eventType = eventType;
        this.useDate = useDate;
        this.amount = amount;
        this.assetType = assetType;
        this.categorySeq = categorySeq;
    }
}

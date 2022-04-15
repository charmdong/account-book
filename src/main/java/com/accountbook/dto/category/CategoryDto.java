package com.accountbook.dto.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CategoryDto {

    private Long seq;
    private String name;
    private EventType eventType;
    private long defaultPrice;
    private List<EcoEvent> ecoEventList = new ArrayList<>();

    public CategoryDto(Category category) {
        this.seq = category.getSeq();
        this.name = category.getName();
        this.eventType = category.getEventType();
        this.defaultPrice = category.getDefaultPrice();
        this.ecoEventList = category.getEcoEventList();
    }
}

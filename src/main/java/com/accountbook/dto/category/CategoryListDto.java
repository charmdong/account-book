package com.accountbook.dto.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.EventType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@RequiredArgsConstructor
public class CategoryListDto {
    
    private Long seq;
    private String name;
    private EventType eventType;
    private long defaultPrice;
    private String useYn;

    public CategoryListDto(Category category) {
        this.seq = category.getSeq();
        this.name = category.getName();
        this.eventType = category.getEventType();
        this.defaultPrice = category.getDefaultPrice();
        this.useYn = category.getUseYn();
    }
}

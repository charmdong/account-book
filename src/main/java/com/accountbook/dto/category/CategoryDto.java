package com.accountbook.dto.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long seq;
    private String name;
    private EventType eventType;

    public CategoryDto(Category category) {
        this.seq = category.getSeq();
        this.name = category.getComCategory().getName();
        this.eventType = category.getComCategory().getEventType();
    }
}

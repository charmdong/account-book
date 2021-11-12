package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.user.CategoryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_CODE")
    private Long code;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private Boolean useYn;
    private Boolean defaultYn;

    // 생성자 메서드
    public static Category createCategory(CategoryRequest request) {
        Category category = new Category();

        category.name = request.getName();
        category.eventType = request.getEventType();
        category.useYn = request.getUseYn();
        category.defaultYn = false;

        return category;
    }
}

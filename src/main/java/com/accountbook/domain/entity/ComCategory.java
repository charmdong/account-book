package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.category.CategoryRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class ComCategory extends BaseInfo {

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
    public static ComCategory createCategory(CategoryRequest request) {
        ComCategory comCategory = new ComCategory();

        comCategory.name = request.getName();
        comCategory.eventType = request.getEventType();
        comCategory.useYn = request.getUseYn();
        comCategory.defaultYn = false;

        return comCategory;
    }
}

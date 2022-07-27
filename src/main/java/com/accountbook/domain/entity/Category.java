package com.accountbook.domain.entity;


import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.category.CategoryRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Entity
@Getter
@ToString(exclude = "ecoEventList")
public class Category extends BaseTimeInfo {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_SEQ")
    private Long seq;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ColumnDefault("0")
    private Long defaultPrice;

    @ColumnDefault("'Y'")
    private String useYn;

    @OneToMany(mappedBy = "category")
    private List<EcoEvent> ecoEventList = new ArrayList<>(); // N+1 위험 (항상 fetch join으로만 조회)

    public static Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.name = categoryRequest.getName();
        category.eventType = categoryRequest.getEventType();
        category.defaultPrice = categoryRequest.getDefaultPrice();
        //category.useYn = "Y";

        return category;
    }

    public void changeCategory(CategoryRequest categoryRequest) {
        if(categoryRequest.getName() != null) {
            this.name = categoryRequest.getName();
        }
        if(categoryRequest.getEventType() != null){
            this.eventType = categoryRequest.getEventType();
        }
        if(categoryRequest.getDefaultPrice() != null){
            this.defaultPrice = categoryRequest.getDefaultPrice();
        }
        if(categoryRequest.getUseYn() != null){
            this.useYn = categoryRequest.getUseYn();
        }
    }

    public void changeUseYn(String useYn, Long defaultPrice) {
        if(useYn != null){
            this.useYn = useYn;
        }
        if(defaultPrice != null){
            this.defaultPrice = defaultPrice;
        }
    }
}


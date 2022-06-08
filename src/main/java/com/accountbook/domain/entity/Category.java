package com.accountbook.domain.entity;


import com.accountbook.domain.enums.EventType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    private Long defaultPrice;

    @OneToMany(mappedBy = "category")
    private List<EcoEvent> ecoEventList = new ArrayList<>(); // N+1 위험 (항상 fetch join으로만 조회)

}


package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
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
}

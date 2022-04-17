package com.accountbook.domain.entity;

<<<<<<< HEAD
import com.accountbook.domain.enums.EventType;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
=======
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.accountbook.domain.enums.EventType;

import lombok.Getter;
import lombok.ToString;
>>>>>>> e6985e13a480fd8003400cf1f849ae659705dd81

@Entity
@Getter
@ToString(exclude = "ecoEventList")
public class Category extends BaseTimeInfo {

    @Id
    @GeneratedValue
    @Column(name = "categorySeq")
    private Long seq;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private Long defaultPrice;

    @OneToMany
    private List<EcoEvent> ecoEventList = new ArrayList<>(); // N+1 위험 (항상 fetch join으로만 조회)

<<<<<<< HEAD
}
=======
}
>>>>>>> e6985e13a480fd8003400cf1f849ae659705dd81

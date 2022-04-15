package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

}

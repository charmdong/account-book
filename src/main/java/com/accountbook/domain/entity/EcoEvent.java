package com.accountbook.domain.entity;

import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.enums.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EcoEvent {

    @Id
    @GeneratedValue
    @Column(name = "EVENT_SEQ")
    private Long seq;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime useDate;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @OneToOne
    private Category category;
}

package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EcoEvent extends BaseInfo {

    //금융 이벤트 고유 번호
    @Id
    @GeneratedValue
    @Column(name = "ECO_EVENT_SEQ")
    private Long seq;

    // 등록 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    //금융 이벤트 타입 (수입/지출)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    //금융 이벤트 발생 일자
    private LocalDateTime useDate;

    //금융 이벤트 발생 금액
    private Long amount;

    //금융 이벤트 설명
    private String desc;

    //금융 이벤트 분류
    @OneToOne
    @JoinColumn(name = "CATEGORY_SEQ")
    private Category category;

    //생성 메소드
    public static EcoEvent createEcoEvent(EcoEventRequest ecoEventRequest, User user, Category category){
        EcoEvent ecoEvent = new EcoEvent();
        ecoEvent.user = user;
        ecoEvent.eventType = ecoEventRequest.getEventType();
        ecoEvent.useDate = ecoEventRequest.getUseDate();
        ecoEvent.amount = ecoEventRequest.getAmount();
        ecoEvent.category = category;
        return ecoEvent;
    }

    //비즈니스 로직
    public void changeEcoEvent(EcoEventRequest ecoEventRequest, Category category){
        if(ecoEventRequest.getEventType() != null){
            this.eventType = eventType;
        }
        if(ecoEventRequest.getUseDate() != null) {
            this.useDate = ecoEventRequest.getUseDate();
        }
        if(ecoEventRequest.getAmount() != null) {
            this.amount = ecoEventRequest.getAmount();
        }
        if(ecoEventRequest.getCategorySeq() != null){
            this.category = category;
        }
    }
}

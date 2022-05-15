package com.accountbook.domain.repository.ecoEvent;

import java.time.LocalDateTime;
import java.util.List;

import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.EventType;

public interface EcoEventRepositoryCustom {
    void saveEcoEvent(EcoEvent ecoEvent);

    //금융 이벤트 조회 by User
    List<EcoEvent> findByUserId(String userId);

    List<EcoEvent> findByEventTypeAndUseDate(String userId, LocalDateTime startDate, LocalDateTime endDate, EventType eventType);
}

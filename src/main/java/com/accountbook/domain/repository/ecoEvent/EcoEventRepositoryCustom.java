package com.accountbook.domain.repository.ecoEvent;

import com.accountbook.domain.entity.EcoEvent;

import java.util.List;

public interface EcoEventRepositoryCustom {
    void saveEcoEvent(EcoEvent ecoEvent);

    //금융 이벤트 조회 by User
    List<EcoEvent> findByUserId(String userId);
}

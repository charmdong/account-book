package com.accountbook.domain.repository.ecoEvent;

import com.accountbook.domain.entity.EcoEvent;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class EcoEventRepositoryCustomImpl implements  EcoEventRepositoryCustom{

    private EntityManager em;

    public void saveEcoEvent(EcoEvent ecoEvent){
        em.persist(ecoEvent);
    }
}
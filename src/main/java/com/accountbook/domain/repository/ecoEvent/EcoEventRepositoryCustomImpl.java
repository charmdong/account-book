package com.accountbook.domain.repository.ecoEvent;

import com.accountbook.domain.entity.EcoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class EcoEventRepositoryCustomImpl implements EcoEventRepositoryCustom{

    private EntityManager em;

    public void saveEcoEvent(EcoEvent ecoEvent){
        em.persist(ecoEvent);
    }

    @Override
    public List<EcoEvent> findByUserId(String userId) {
        String jpql = "select e from EcoEvent e join fetch Category c where e.category = c.seq and c.user = :userId";
        return em.createQuery(jpql).setParameter("userId", userId).getResultList();
    }
}
package com.accountbook.domain.repository.ecoEvent;

import com.accountbook.domain.entity.EcoEvent;
import com.accountbook.domain.enums.EventType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static com.accountbook.domain.entity.QEcoEvent.ecoEvent;

@RequiredArgsConstructor
public class EcoEventRepositoryCustomImpl implements EcoEventRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void saveEcoEvent(EcoEvent ecoEvent){
        em.persist(ecoEvent);
    }

    @Override
    public List<EcoEvent> findByUserId(String userId) {
        String jpql = "select e " +
                      "from EcoEvent e " +
                      "where e.user.id = :userId";
        return em.createQuery(jpql, EcoEvent.class)
                 .setParameter("userId", userId)
                 .getResultList();
    }

    @Override
    public List<EcoEvent> findByEventTypeAndUseDate(String userId, LocalDateTime startDate, LocalDateTime endDate, EventType eventType) {
        String jpql = "select e " +
                "from EcoEvent e " +
                "join fetch e.category c " +
                "where e.user.id = :userId ";
        if(startDate != null) {
            jpql += "and e.useDate >= :startDate ";
        }
        if(endDate != null) {
            jpql += "and e.useDate <= :endDate ";
        }
        if(eventType != null) {
            jpql += "and e.eventType = :eventType";
        }

        TypedQuery query = em.createQuery(jpql, EcoEvent.class)
                             .setParameter("userId", userId);
        if(startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if(endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if(eventType != null){
            query.setParameter("eventType", eventType);
        }
        return query.getResultList();
    }

    /**
     * startDate ~ endDate 에 해당하는 금융 이벤트 조회
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<EcoEvent> findByUseDate (String userId, LocalDateTime startDate, LocalDateTime endDate) {

        return queryFactory.selectFrom(ecoEvent)
                .where(ecoEvent.user.id.eq(userId),
                        ecoEvent.useDate.between(startDate, endDate))
                .fetch();
    }
}
package com.accountbook.domain.repository;

import com.accountbook.domain.entity.EcoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EcoEventRepository {

    private final EntityManager em;

    //금융 이벤트 전체 조회
    public List<EcoEvent> findAll(){
        return em.createQuery("select e from eco_event e", EcoEvent.class)
                 .getResultList();
    }

    //금융 이벤트 User 조회
    public List<EcoEvent> findByUser(String userId){
        return em.createQuery("select e from eco_event e join fetch user u where u.id = :userId", EcoEvent.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    //금융 이벤트 상세 조회
    public EcoEvent findBySeq(Long ecoEventSeq){
        return em.find(EcoEvent.class, ecoEventSeq);
    }

    //금융 이벤트 저장
    public void save(EcoEvent ecoEvent){
        em.persist(ecoEvent);
    }

    //금융 이벤트 삭제
    public void remove(EcoEvent ecoEvent){
        em.remove(ecoEvent );
    }
}

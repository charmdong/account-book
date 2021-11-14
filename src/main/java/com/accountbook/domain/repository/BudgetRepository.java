package com.accountbook.domain.repository;

import com.accountbook.domain.entity.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BudgetRepository{

    private final EntityManager em;

    //예산 전체 조회
    public List<Budget> findAll(){
        return em.createQuery("select b from Budget b", Budget.class)
                 .getResultList();
    }

    //예산 상세 조회
    public Budget findById(Long id){
        return em.find(Budget.class, id);
    }

    //예산 저장
    public void save(Budget budget){
        em.persist(budget);
    }
    //예산 삭
    public void remove(Budget budget){
        em.remove(budget);
    }
}

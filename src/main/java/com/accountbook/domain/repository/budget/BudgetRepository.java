package com.accountbook.domain.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>, BudgetRepositoryCustom {

    //예산 전체 조회
    List<Budget> findAll();

    //예산 상세 조회
    Optional<Budget> findBySeq(Long budgetSeq);

    //예산 조회 by User
    List<Budget> findByUserId(String userId);

    void deleteBySeq(Long budgetSeq);
}

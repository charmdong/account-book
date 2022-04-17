package com.accountbook.domain.repository.budget;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class BudgetRepositoryCustomImpl implements BudgetRepositoryCustom{

   private final EntityManager em;

    @Override
    public void saveBudget(Budget budget) {
        em.persist(budget);
    }

}

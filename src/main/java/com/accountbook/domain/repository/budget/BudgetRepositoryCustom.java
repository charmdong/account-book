package com.accountbook.domain.repository.budget;

import com.accountbook.domain.entity.Budget;

public interface BudgetRepositoryCustom {
    //예산 저장
    void saveBudget(Budget budget);
}

package com.accountbook.domain.repository.budget;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.entity.QAsset;
import com.accountbook.domain.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class BudgetRepositoryCustomImpl implements BudgetRepositoryCustom{

   private final EntityManager em;

    @Override
    public void saveBudget(Budget budget) {
        em.persist(budget);
    }

}

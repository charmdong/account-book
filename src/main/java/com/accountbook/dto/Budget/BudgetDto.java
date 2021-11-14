package com.accountbook.dto.Budget;

import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.PeriodType;
import lombok.Data;

@Data
public class BudgetDto {
    private Long seq;

    private Category category;

    private PeriodType periodType;

    private Long amount;

    private User user;

    public BudgetDto(Long seq, Category category, PeriodType periodType, Long amount, User user) {
        this.seq = seq;
        this.category = category;
        this.periodType = periodType;
        this.amount = amount;
        this.user = user;
    }
    public BudgetDto(Budget budget){
        this.seq = budget.getSeq();
        this.category = budget.getCategory();
        this.periodType = budget.getPeriodType();
        this.amount = budget.getAmount();
        this.user = budget.getUser();
    }
}

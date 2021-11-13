package com.accountbook.dto.Budget;

import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.UserCategory;
import com.accountbook.domain.enums.PeriodType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BudgetDto {
    private Long seq;

    @NotEmpty
    private UserCategory category;

    @NotEmpty
    private PeriodType periodType;

    @NotEmpty
    private Long amount;

    @NotEmpty
    private User user;

    public BudgetDto(Long seq, UserCategory category, PeriodType periodType, Long amount, User user) {
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

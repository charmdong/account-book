package com.accountbook.dto.Budget;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.UserCategory;
import com.accountbook.domain.enums.PeriodType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class BudgetRequest {

    @NotEmpty
    private UserCategory category;

    @NotEmpty
    private PeriodType periodType;

    @NotEmpty
    private Long amount;

    @NotEmpty
    private User user;


    public BudgetRequest(UserCategory category, PeriodType periodType, Long amount, User user) {
        this.category = category;
        this.periodType = periodType;
        this.amount = amount;
        this.user = user;
    }
}

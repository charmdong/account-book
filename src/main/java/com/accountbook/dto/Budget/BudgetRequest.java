package com.accountbook.dto.Budget;

import com.accountbook.domain.enums.PeriodType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class BudgetRequest {

    @NotEmpty
    private Long categorySeq;

    @NotEmpty
    private PeriodType periodType;

    @NotEmpty
    private Long amount;

    @NotEmpty
    private String userId;


    public BudgetRequest(Long categorySeq, PeriodType periodType, Long amount, String userId) {
        this.categorySeq = categorySeq;
        this.periodType = periodType;
        this.amount = amount;
        this.userId = userId;
    }
}

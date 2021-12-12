package com.accountbook.dto.Budget;

import com.accountbook.domain.enums.PeriodType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class BudgetRequest {

    @NotEmpty(message = "카테고리 누락")
    private Long categorySeq;

    @NotEmpty(message = "기간 타입 누락")
    private PeriodType periodType;

    @NotEmpty(message = "금액 누락")
    private Long amount;

    @NotEmpty(message = "유저 아이디 누락")
    private String userId;


    public BudgetRequest(Long categorySeq, PeriodType periodType, Long amount, String userId) {
        this.categorySeq = categorySeq;
        this.periodType = periodType;
        this.amount = amount;
        this.userId = userId;
    }
}

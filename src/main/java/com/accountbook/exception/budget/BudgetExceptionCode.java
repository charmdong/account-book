package com.accountbook.exception.budget;

import lombok.Getter;

@Getter
public enum BudgetExceptionCode {

    NOT_EXPECTED_ERROR(4000L, "Not Expected Error."),
    BUDGET_NOT_FOUND(4001L, "Budget does not existed."),
    CATEGORY_NOT_FOUND(4003L,"Check related Category");

    public Long code;
    public String msg;

    BudgetExceptionCode(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

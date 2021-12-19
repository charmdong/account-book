package com.accountbook.exception.budget;

import lombok.Getter;

@Getter
public enum BudgetExceptionCode {

    NOT_EXPECTED_ERROR(4000, "Not Expected Error."),
    BUDGET_NOT_FOUND(4001, "Budget does not existed."),
    ACCESS_EMPTY_DATA(4002, "Access Empty Data"),
    CATEGORY_NOT_FOUND(4003,"Check related Category");


    public int code;
    public String msg;

    BudgetExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

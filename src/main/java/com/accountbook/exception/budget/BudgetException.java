package com.accountbook.exception.budget;

import lombok.Getter;

@Getter
public class BudgetException extends RuntimeException{
    private BudgetExceptionCode budgetExceptionCode;

    public BudgetException() {
        super();
    }
    public BudgetException(String message) {
        super(message);
    }

    public BudgetException(BudgetExceptionCode budgetExceptionCode) {
        this.budgetExceptionCode = budgetExceptionCode;
    }

}

package com.accountbook.exception.budget;

public class BudgetUpdateException extends RuntimeException{
    public BudgetUpdateException() {
        super();
    }

    public BudgetUpdateException(String message) {
        super(message);
    }
}

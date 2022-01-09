package com.accountbook.exception.budget;

public class RelatedCategoryNotFoundException extends BudgetException{
    public RelatedCategoryNotFoundException() {
        super();
    }

    public RelatedCategoryNotFoundException(String message) {
        super(message);
    }
}

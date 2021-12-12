package com.accountbook.exception.budget;

public class RelatedCategoryNotFoundException extends RuntimeException{
    public RelatedCategoryNotFoundException() {
        super();
    }

    public RelatedCategoryNotFoundException(String message) {
        super(message);
    }
}

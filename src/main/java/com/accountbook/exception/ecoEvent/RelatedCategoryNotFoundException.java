package com.accountbook.exception.ecoEvent;

public class RelatedCategoryNotFoundException extends RuntimeException{
    public RelatedCategoryNotFoundException() {
        super();
    }

    public RelatedCategoryNotFoundException(String message) {
        super(message);
    }
}

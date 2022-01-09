package com.accountbook.exception.ecoEvent;

public class RelatedCategoryNotFoundException extends EcoEventException{
    public RelatedCategoryNotFoundException() {
        super();
    }

    public RelatedCategoryNotFoundException(String message) {
        super(message);
    }
}

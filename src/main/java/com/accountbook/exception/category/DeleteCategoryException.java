package com.accountbook.exception.category;

import com.accountbook.domain.entity.Category;

public class DeleteCategoryException extends CategoryException {

    public DeleteCategoryException() {
        super();
    }

    public DeleteCategoryException(String message) {
        super(message);
    }
    
}

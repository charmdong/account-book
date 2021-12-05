package com.accountbook.exception.category;

import com.accountbook.domain.entity.Category;

public class CategoryNotDeletedException extends CategoryException {

    public CategoryNotDeletedException() {
        super();
    }

    public CategoryNotDeletedException(String message) {
        super(message);
    }
    
}

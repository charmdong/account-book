package com.accountbook.exception.category;

import lombok.Getter;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
@Getter
public class CategoryException extends RuntimeException {

    private CategoryExceptionCode categoryExceptionCode;

    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(CategoryExceptionCode categoryExceptionCode) {
        this.categoryExceptionCode = categoryExceptionCode;
    }
}

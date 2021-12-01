package com.accountbook.exception.category;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class CategoryException extends RuntimeException {

    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }
}

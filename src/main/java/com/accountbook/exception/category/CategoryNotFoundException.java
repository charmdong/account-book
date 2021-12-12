package com.accountbook.exception.category;

/**
 * CategoryNotFoundException
 *
 * @author donggun
 * @since 2021/12/12
 */
public class CategoryNotFoundException extends CategoryException {

    public CategoryNotFoundException() { /* empty */ }

    public CategoryNotFoundException(String message) {
        super(message);
    }

}

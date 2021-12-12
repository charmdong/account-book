package com.accountbook.exception.category;

/**
 * CategoryNotInsertedException
 *
 * @author donggun
 * @since 2021/12/12
 */
public class CategoryNotInsertedException extends CategoryException {

    public CategoryNotInsertedException() { /* empty */ }

    public CategoryNotInsertedException(String message) {
        super(message);
    }
}

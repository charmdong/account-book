package com.accountbook.exception.category;

/**
 * CategoryNotInsertedException
 *
 * @author donggun
 * @since 2021/12/12
 */
public class InsertCategoryException extends CategoryException {

    public InsertCategoryException() { /* empty */ }

    public InsertCategoryException(String message) {
        super(message);
    }

    public InsertCategoryException(CategoryExceptionCode categoryExceptionCode) {
        super(categoryExceptionCode);
    }
}

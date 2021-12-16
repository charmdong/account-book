package com.accountbook.exception.category;

/**
 * UpdateCategoryException
 *
 * @author donggun
 * @since 2021/12/16
 */
public class UpdateCategoryException extends CategoryException {

    public UpdateCategoryException() { /* empty */ }

    public UpdateCategoryException(String message) {
        super(message);
    }

    public UpdateCategoryException(CategoryExceptionCode categoryExceptionCode) {
        super(categoryExceptionCode);
    }
}

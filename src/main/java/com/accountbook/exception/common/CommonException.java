package com.accountbook.exception.common;

/**
 * CommonException
 *
 * @author donggun
 * @since 2021/12/17
 */
public class CommonException extends RuntimeException {

    public CommonException() { /* empty */ }

    public CommonException(String message) {
        super(message);
    }
}
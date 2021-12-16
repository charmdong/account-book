package com.accountbook.exception.user;

import lombok.Getter;

/**
 * UserException
 *
 * @author donggun
 * @since 2021/12/16
 */
@Getter
public class UserException extends RuntimeException {

    private UserExceptionCode userExceptionCode;

    public UserException() { /* empty */ }

    public UserException(String message) {
        super(message);
    }

    public UserException(UserExceptionCode userExceptionCode) {
        this.userExceptionCode = userExceptionCode;
    }
}

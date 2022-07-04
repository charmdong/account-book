package com.accountbook.exception.user;

/**
 * UpdateUserException
 *
 * @author donggun
 * @since 2021/12/21
 */
public class UpdateUserException extends UserException {

    public UpdateUserException() { /* empty */}

    public UpdateUserException(String message) {
        super(message);
    }

    public UpdateUserException(UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }
}

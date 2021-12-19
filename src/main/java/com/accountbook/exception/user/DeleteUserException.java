package com.accountbook.exception.user;

/**
 * UserNotDeletedException
 *
 * @author donggun
 * @since 2021/12/15
 */
public class DeleteUserException extends UserException {

    public DeleteUserException() { /* empty */ }

    public DeleteUserException(String message) {
        super(message);
    }

    public DeleteUserException(UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }
}

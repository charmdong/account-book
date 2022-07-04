package com.accountbook.exception.user;

/**
 * UserNotInsertedException
 *
 * @author donggun
 * @since 2021/12/15
 */
public class InsertUserException extends UserException {

    public InsertUserException() { /* empty */ }

    public InsertUserException(String message) {
        super(message);
    }

    public InsertUserException(UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }
}

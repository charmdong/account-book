package com.accountbook.exception.user;

/**
 * UserNotInsertedException
 *
 * @author donggun
 * @since 2021/12/15
 */
public class UserNotInsertedException extends UserException {

    public UserNotInsertedException() { /* empty */ }

    public UserNotInsertedException(String message) {
        super(message);
    }
}

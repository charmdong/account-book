package com.accountbook.exception.user;

/**
 * UserNotDeletedException
 *
 * @author donggun
 * @since 2021/12/15
 */
public class UserNotDeletedException extends UserException {

    public UserNotDeletedException() { /* empty */ }

    public UserNotDeletedException(String message) {
        super(message);
    }
}

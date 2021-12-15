package com.accountbook.exception.user;

/**
 * UserNotFoundException
 *
 * @author donggun
 * @since 2021/12/14
 */
public class UserNotFoundException extends UserException {

    public UserNotFoundException() { /* empty */ }

    public UserNotFoundException(String message) {
        super(message);
    }
}

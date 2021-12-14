package com.accountbook.exception.user;

public class UserNotFoundException extends UserException {

    public UserNotFoundException() { /* empty */ }

    public UserNotFoundException(String message) {
        super(message);
    }
}

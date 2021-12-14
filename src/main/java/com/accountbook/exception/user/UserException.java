package com.accountbook.exception.user;

public class UserException extends RuntimeException {

    public UserException() { /* empty */ }

    public UserException(String message) {
        super(message);
    }
}

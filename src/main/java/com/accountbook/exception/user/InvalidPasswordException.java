package com.accountbook.exception.user;

/**
 * InvalidPasswordException
 *
 * @author donggun
 * @since 2022/02/28
 */
public class InvalidPasswordException extends UserException {

    public InvalidPasswordException () {
    }

    public InvalidPasswordException (String message) {
        super(message);
    }

    public InvalidPasswordException (UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }
}

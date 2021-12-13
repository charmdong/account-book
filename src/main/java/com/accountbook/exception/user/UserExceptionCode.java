package com.accountbook.exception.user;

import lombok.Getter;

/**
 * UserExceptionCode
 *
 * @author donggun
 * @since 2021/12/13
 */
@Getter
public enum UserExceptionCode {

    INSERT_FAIL (3001L, ""),
    NOT_FOUND (3002L, ""),
    UPDATE_FAIL (3003L, ""),
    DELETE_FAIL (3004L, "")

    ;

    private Long code;
    private String message;

    UserExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

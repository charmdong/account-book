package com.accountbook.exception.common;

import lombok.Getter;

/**
 * CommonExceptionCode
 *
 * @author donggun
 * @since 2021/12/16
 */
@Getter
public enum CommonExceptionCode {

    UNEXPECTED_EX(9000L, "Unexpected Exception 발생"),
    INVALID_PARAM(9001L, "유효성 오류"),
    ;

    private Long code;
    private String message;

    CommonExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

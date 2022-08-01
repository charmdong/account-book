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

    UNEXPECTED_EX(9000L, "시스템 오류가 발생헸습니다. 관리자에게 문의 바랍니다."),
    INVALID_PARAM(9001L, "유효성 오류"),
    MISSING_SERVLET_REQUEST_PARAM(9003L, "필수 요청 파라미터가 존재하지 않습니다."),
    ;

    private Long code;
    private String message;

    CommonExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.accountbook.exception.account;

import lombok.Getter;

@Getter
public enum AssetExceptionCode {

    NOT_FOUND_ACCOUNT(1000L, ""),
    FAILED_REGIST_ACCOUNT(1001L, ""),
    FAILED_UPDATE_ACCOUNT(1002L, ""),
    FAILED_DELETE_ACCOUNT(1003L, "");

    private Long code;
    private String message;

    private AssetExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }

}

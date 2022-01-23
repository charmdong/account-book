package com.accountbook.exception.account;

import lombok.Getter;

@Getter
public enum AssetExceptionCode {

    NOT_FOUND_ACCOUNT(1000L, "Not found account"),
    FAILED_REGIST_ACCOUNT(1001L, "failed to regist account"),
    FAILED_UPDATE_ACCOUNT(1002L, "failed to update account"),
    FAILED_DELETE_ACCOUNT(1003L, "failed to delete account");

    private Long code;
    private String message;

    private AssetExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }

}

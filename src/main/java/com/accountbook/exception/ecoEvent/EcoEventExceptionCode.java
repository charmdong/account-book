package com.accountbook.exception.ecoEvent;

import lombok.Getter;

@Getter
public enum EcoEventExceptionCode {

    NOT_EXPECTED_ERROR(5000, "Not expected error."),
    ECOEVENT_NOT_FOUND(5001, "EcoEvent does not existed."),
    ACCESS_EMPTY_DATA(5002, "Access empty data"),
    CATEGORY_NOT_FOUND(5003,"Check related Category");

    public int code;
    public String msg;

    EcoEventExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

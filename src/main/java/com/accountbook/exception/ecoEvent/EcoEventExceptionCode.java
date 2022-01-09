package com.accountbook.exception.ecoEvent;

import lombok.Getter;

@Getter
public enum EcoEventExceptionCode {

    NOT_EXPECTED_ERROR(5000L, "Not expected error."),
    ECOEVENT_NOT_FOUND(5001L, "EcoEvent does not existed."),
    CATEGORY_NOT_FOUND(5003L,"Check related Category");

    public Long code;
    public String msg;

    EcoEventExceptionCode(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.accountbook.exception.ecoEvent;

import lombok.Getter;

@Getter
public enum EcoEventExceptionCode {

    NOT_EXPECTED_ERROR(5000L, "서버 오류입니다."),
    NOT_FOUND_ECOEVENT(5001L, "금융이벤트가 존재하지 않습니다."),
    NOT_FOUND_CATEGORY(5002L,"카테고리가 존재하지 않습니다."),
    NOT_FOUND_USER(5003L, "사용자가 존재하지 않습니다.");

    public Long code;
    public String message;

    EcoEventExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

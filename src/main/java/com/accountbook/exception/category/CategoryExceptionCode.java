package com.accountbook.exception.category;

import lombok.Getter;

/**
 * CategoryExceptionCode
 *
 * @author donggun
 * @since 2021/12/12
 */

@Getter
public enum CategoryExceptionCode {

    INSERT_FAIL(2001L, "카테고리 추가 중 오류가 발생했습니다."),
    NOT_FOUND(2002L, "카테고리 정보가 존재하지 않습니다."),
    UPDATE_FAIL(2003L, "카테고리 정보 갱신 중 오류가 발생했습니다."),
    DELETE_FAIL(2004L, "카테고리 정보 삭제 중 오류가 발생했습니다."),

    ;

    private Long code;
    private String message;

    CategoryExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

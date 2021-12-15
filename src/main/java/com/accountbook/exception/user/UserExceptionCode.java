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

    INSERT_FAIL (3001L, "사용자 등록에 실패했습니다."),
    NOT_FOUND (3002L, "사용자 조회에 실패했습니다."),
    UPDATE_FAIL (3003L, "사용자 수정에 실패했습니다."),
    DELETE_FAIL (3004L, "사용자 삭제에 실패했습니다.")

    ;

    private Long code;
    private String message;

    UserExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

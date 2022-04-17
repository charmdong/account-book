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

    INSERT_FAIL (3000L, "사용자 등록에 실패했습니다."),
    SETTING_FAIL(3001L, "사용자 설정에 실패했습니다."),
    NOT_FOUND (3002L, "사용자 조회에 실패했습니다."),
    UPDATE_FAIL (3003L, "사용자 수정에 실패했습니다."),
    DELETE_FAIL (3004L, "사용자 삭제에 실패했습니다."),
    PWD_UPDATE_FAIL (3005L, "패스워드 변경에 실패했습니다."),
    INVALID_PWD (3006L, "패스워드가 일치하지 않습니다."),
    INVALID_SESSION_ID (3007L, "세션 아이디가 일치하지 않습니다."),
    SETTING_NOT_FOUND(3008L, "사용자 설정 정보 조회에 실패했습니다."),

    ;

    private Long code;
    private String message;

    UserExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

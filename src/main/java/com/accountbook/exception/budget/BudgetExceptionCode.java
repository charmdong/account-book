package com.accountbook.exception.budget;

import lombok.Getter;

@Getter
public enum BudgetExceptionCode {

    NOT_EXPECTED_ERROR(4000L, "서버 오류입니다."),
    NOT_FOUND_BUDGET(4001L, "예산을 찾을 수 없습니다."),
    NOT_FOUND_CATEGORY(4002L,"카테고리를 찾을 수 없습니다.");

    private Long code;
    private String message;

    BudgetExceptionCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}

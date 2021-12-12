package com.accountbook.exception.user;

import com.accountbook.api.UserApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class UserExceptionHandler {


    /**
     * 상세 조회 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse NoSuchElementExceptionHandler(NoSuchElementException e) {

        return new ApiResponse(CategoryExceptionCode.FIND_DETAIL_ERROR, HttpStatus.EXPECTATION_FAILED, ""); // TODO 메시지 관리

    }
}

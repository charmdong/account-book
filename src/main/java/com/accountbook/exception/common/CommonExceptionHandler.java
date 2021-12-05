package com.accountbook.exception.common;

import com.accountbook.dto.asset.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.accountbook.api"})
public class CommonExceptionHandler {

    /**
     * @Valid Exception Handler
     *
     * @param e
     * @return 예외 정보
     */
    @ExceptionHandler
    public ApiResponse validExceptionHandler(MethodArgumentNotValidException e) {

        return new ApiResponse(e, HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}

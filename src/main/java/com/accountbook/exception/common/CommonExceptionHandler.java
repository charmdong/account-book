package com.accountbook.exception.common;

import com.accountbook.dto.asset.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.accountbook.api"})
public class CommonExceptionHandler {

    @ExceptionHandler
    public ApiResponse validExceptionHandler(MethodArgumentNotValidException e) {

        return new ApiResponse(
                CommonExceptionCode.INVALID_PARAM.getCode(),
                HttpStatus.OK,
                CommonExceptionCode.INVALID_PARAM.getMessage()
        );
    }

    @ExceptionHandler
    public ApiResponse unExpectedExceptionHandler (Exception e) {

        return new ApiResponse(
                CommonExceptionCode.UNEXPECTED_ERROR.getCode(),
                HttpStatus.OK,
                CommonExceptionCode.UNEXPECTED_ERROR.getMessage()
        );
    }
}

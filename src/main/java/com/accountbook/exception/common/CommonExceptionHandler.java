package com.accountbook.exception.common;

import com.accountbook.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.accountbook.api"})
public class CommonExceptionHandler {

    @ExceptionHandler
    public ApiResponse validExceptionHandler(MethodArgumentNotValidException e) {

        log.warn("MethodArgumentNotValidException... ", e);

        return new ApiResponse(
                CommonExceptionCode.INVALID_PARAM.getCode(),
                HttpStatus.BAD_REQUEST,
                CommonExceptionCode.INVALID_PARAM.getMessage()
        );
    }

    @ExceptionHandler
    public ApiResponse missingServletRequestParameterExceptionHandler (MissingServletRequestParameterException e) {

        log.warn("MissingServletRequestParameterException >>> {}", e);

        return new ApiResponse(
                CommonExceptionCode.MISSING_SERVLET_REQUEST_PARAM.getCode(),
                HttpStatus.BAD_REQUEST,
                CommonExceptionCode.MISSING_SERVLET_REQUEST_PARAM.getMessage()
        );
    }

    @ExceptionHandler
    public ApiResponse unExpectedExceptionHandler (Exception e) {

        log.warn("Unexpected Exception >>> {}",  e.getClass(), e);

        return new ApiResponse(
                CommonExceptionCode.UNEXPECTED_EX.getCode(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                CommonExceptionCode.UNEXPECTED_EX.getMessage()
        );
    }
}

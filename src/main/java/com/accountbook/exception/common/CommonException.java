package com.accountbook.exception.common;

import com.accountbook.api.responseModel.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CommonException
 *
 * @author donggun
 * @since 2021/12/17
 */
public class CommonException extends RuntimeException {

    public CommonException() { /* empty */ }

    public CommonException(String message) {
        super(message);
    }
}

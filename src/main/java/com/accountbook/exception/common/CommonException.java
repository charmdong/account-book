package com.accountbook.exception.common;

import com.accountbook.dto.asset.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class CommonException extends RuntimeException {

    private int code;
    private String customMessage;

    public CommonException(int code, String message) {
    	super(message, new Throwable());
        this.code = code;
        this.customMessage = message;
    }
}

package com.accountbook.exception.user;

import com.accountbook.api.UserApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class UserExceptionHandler {



}

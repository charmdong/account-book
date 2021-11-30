package com.accountbook.exception.handler;

import com.accountbook.api.CategoryApiController;
import com.accountbook.api.UserApiController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class UserExceptionHandler {


}

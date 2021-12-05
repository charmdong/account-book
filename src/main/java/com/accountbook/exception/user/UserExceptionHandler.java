package com.accountbook.exception.user;

import com.accountbook.api.UserApiController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class UserExceptionHandler {


}

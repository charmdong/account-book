package com.accountbook.exception.handler;

import com.accountbook.api.CategoryApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {CategoryApiController.class})
public class CategoryExceptionHandler {


}

package com.accountbook.exception.category;

import com.accountbook.api.CategoryApiController;
import com.accountbook.api.responseModel.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CategoryExceptionHandler
 *
 * @author donggun
 * @since 2021/12/01
 */
@RestControllerAdvice(basePackageClasses = {CategoryApiController.class})
public class CategoryExceptionHandler {

    @ExceptionHandler(CategoryException.class)
    public ApiResponse CategoryExceptionHandler (CategoryException ce) {

        return new ApiResponse(
                ce.getCategoryExceptionCode().getCode(),
                HttpStatus.OK,
                ce.getCategoryExceptionCode().getMessage()
        );
    }

}

package com.accountbook.exception.category;

import com.accountbook.api.CategoryApiController;
import com.accountbook.dto.asset.ApiResponse;
import com.accountbook.exception.category.CategoryException;
import com.accountbook.exception.category.CategoryNotDeletedException;
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

    @ExceptionHandler
    public ApiResponse CategoryExceptionHandler(CategoryException ce) {

        if(ce instanceof CategoryNotDeletedException) {
            return new ApiResponse("", HttpStatus.EXPECTATION_FAILED, ce.getMessage());
        }

        else {
            return new ApiResponse("", HttpStatus.OK, ce.getMessage());
        }
    }
}

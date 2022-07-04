package com.accountbook.exception.category;

import com.accountbook.api.CategoryApiController;
import com.accountbook.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CategoryExceptionHandler
 *
 * @author donggun
 * @since 2021/12/01
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {CategoryApiController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CategoryExceptionHandler {

    @ExceptionHandler(CategoryException.class)
    public ApiResponse CategoryExceptionHandler (CategoryException ce) {

        log.warn("{}...", ce.getClass(), ce);

        return new ApiResponse(
                ce.getCategoryExceptionCode().getCode(),
                HttpStatus.OK,
                ce.getCategoryExceptionCode().getMessage()
        );
    }

}

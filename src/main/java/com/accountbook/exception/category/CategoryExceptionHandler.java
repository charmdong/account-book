package com.accountbook.exception.category;

import com.accountbook.api.CategoryApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

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

    /**
     * 상세 조회 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse NoSuchElementExceptionHandler(NoSuchElementException e) {

        return new ApiResponse(CategoryExceptionCode.FIND_DETAIL_ERROR, HttpStatus.EXPECTATION_FAILED, ""); // TODO 메시지 관리

    }
}

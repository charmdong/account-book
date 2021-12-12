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

    // TODO Design Pattern 을 적용해서 중복을 줄일 수 있지 않을까?

    /**
     * 카테고리 정보 추가 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse insertExceptionHandler (CategoryNotInsertedException e) {

        return new ApiResponse(
                CategoryExceptionCode.INSERT_FAIL.getCode(),
                HttpStatus.EXPECTATION_FAILED,
                CategoryExceptionCode.INSERT_FAIL.getMessage()
        );
    }

    /**
     * 카테고리 정보 조회 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse foundExceptionHandler (CategoryNotFoundException e) {

        return new ApiResponse(
                CategoryExceptionCode.NOT_FOUND.getCode(),
                HttpStatus.EXPECTATION_FAILED,
                CategoryExceptionCode.NOT_FOUND.getMessage()
        );
    }

    /**
     * 카테고리 정보 수정 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse updateExceptionHandler (CategoryNotUpdatedException e) {

        return new ApiResponse(
                CategoryExceptionCode.UPDATE_FAIL.getCode(),
                HttpStatus.EXPECTATION_FAILED,
                CategoryExceptionCode.UPDATE_FAIL.getMessage()
        );
    }

    /**
     * 카테고리 정보 삭제 예외
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse deleteExceptionHandler (CategoryNotDeletedException e) {

        return new ApiResponse(
                CategoryExceptionCode.DELETE_FAIL.getCode(),
                HttpStatus.EXPECTATION_FAILED,
                CategoryExceptionCode.DELETE_FAIL.getMessage()
        );
    }
}

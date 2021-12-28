package com.accountbook.exception.budget;

import com.accountbook.api.BudgetApiController;
import com.accountbook.dto.response.ApiResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {BudgetApiController.class})
public class BudgetExceptionHandler {

    @ExceptionHandler
    public ApiResponse NoSuchElementExceptionHandler(NoSuchElementException e){
        return new ApiResponse(BudgetExceptionCode.BUDGET_NOT_FOUND.getCode(), HttpStatus.EXPECTATION_FAILED,BudgetExceptionCode.BUDGET_NOT_FOUND.getMsg());
    }

    @ExceptionHandler
    public ApiResponse EmptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e){
        return new ApiResponse(BudgetExceptionCode.ACCESS_EMPTY_DATA.getCode(), HttpStatus.EXPECTATION_FAILED,BudgetExceptionCode.ACCESS_EMPTY_DATA.getMsg());
    }

    @ExceptionHandler
    public ApiResponse NotExpectedExceptionHandler(Exception e){
        return new ApiResponse(BudgetExceptionCode.NOT_EXPECTED_ERROR.getCode(),HttpStatus.EXPECTATION_FAILED,BudgetExceptionCode.NOT_EXPECTED_ERROR.getMsg());
    }
}

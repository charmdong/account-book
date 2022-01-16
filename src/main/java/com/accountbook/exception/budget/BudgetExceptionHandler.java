package com.accountbook.exception.budget;

import com.accountbook.api.BudgetApiController;
import com.accountbook.dto.response.ApiResponse;

import com.accountbook.exception.common.CommonExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {BudgetApiController.class})
public class BudgetExceptionHandler {

    @ExceptionHandler
    public ApiResponse BudgetExceptionHandler(BudgetException e) {

        return new ApiResponse(
                e.getBudgetExceptionCode().getCode(),
                HttpStatus.OK,
                e.getBudgetExceptionCode().getMessage()
        );
    }
}

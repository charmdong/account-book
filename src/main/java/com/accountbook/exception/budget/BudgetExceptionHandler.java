package com.accountbook.exception.budget;

import com.accountbook.api.BudgetApiController;
import com.accountbook.dto.response.ApiResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {BudgetApiController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
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

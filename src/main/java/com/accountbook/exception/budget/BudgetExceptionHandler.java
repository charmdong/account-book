package com.accountbook.exception.budget;

import com.accountbook.api.BudgetApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {BudgetApiController.class})
public class BudgetExceptionHandler {

    @ExceptionHandler
    public ApiResponse BudgetException(BudgetException e){
        e.printStackTrace();
        return new ApiResponse(e.getBudgetExceptionCode().getCode(), HttpStatus.OK,e.getBudgetExceptionCode().getMsg());
    }
}

package com.accountbook.exception.ecoEvent;

import com.accountbook.api.EcoEventApiController;
import com.accountbook.api.responseModel.ApiResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {EcoEventApiController.class})
public class EcoEventExceptionHandler {

    @ExceptionHandler
    public ApiResponse NoSuchElementExceptionHandler(NoSuchElementException e){
        return new ApiResponse(EcoEventExceptionCode.ECOEVENT_NOT_FOUND.getCode(), HttpStatus.EXPECTATION_FAILED,EcoEventExceptionCode.ECOEVENT_NOT_FOUND.getMsg());
    }

    @ExceptionHandler
    public ApiResponse RelatedCategoryNotFoundHandler(RelatedCategoryNotFoundException e){
        return new ApiResponse(EcoEventExceptionCode.CATEGORY_NOT_FOUND.getCode(), HttpStatus.EXPECTATION_FAILED,EcoEventExceptionCode.CATEGORY_NOT_FOUND.getMsg());
    }

    @ExceptionHandler
    public ApiResponse EmptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e){
        return new ApiResponse(EcoEventExceptionCode.ACCESS_EMPTY_DATA.getCode(), HttpStatus.EXPECTATION_FAILED,EcoEventExceptionCode.ACCESS_EMPTY_DATA.getMsg());
    }
    
    @ExceptionHandler
    public ApiResponse NotExpectedExceptionHandler(Exception e){
        return new ApiResponse(EcoEventExceptionCode.NOT_EXPECTED_ERROR.getCode(),HttpStatus.EXPECTATION_FAILED,EcoEventExceptionCode.NOT_EXPECTED_ERROR.getMsg());
    }

}

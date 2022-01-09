package com.accountbook.exception.ecoEvent;

import com.accountbook.api.EcoEventApiController;
import com.accountbook.dto.response.ApiResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {EcoEventApiController.class})
public class EcoEventExceptionHandler {

    @ExceptionHandler
    public ApiResponse EcoEventExceptionHandler(EcoEventException e){
        return new ApiResponse(e.getEcoEventExceptionCode().getCode(),
                HttpStatus.OK,
                e.getEcoEventExceptionCode().getMessage());
    }
}

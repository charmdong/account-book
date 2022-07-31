package com.accountbook.exception.ecoEvent;

import com.accountbook.api.EcoEventApiController;
import com.accountbook.dto.response.ApiResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {EcoEventApiController.class})
@Order(Ordered.HIGHEST_PRECEDENCE) 
public class EcoEventExceptionHandler {

    @ExceptionHandler
    public ApiResponse EcoEventExceptionHandler(EcoEventException e){
        return new ApiResponse(e.getEcoEventExceptionCode().getCode(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getEcoEventExceptionCode().getMessage());
    }
}

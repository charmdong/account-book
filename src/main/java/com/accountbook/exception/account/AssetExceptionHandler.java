package com.accountbook.exception.account;

import com.accountbook.dto.response.ApiResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {AssetApiController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AssetExceptionHandler {

    @ExceptionHandler(AssetException.class)
    public ApiResponse AssetExceptionHandler(AssetException assetException) {

        return new ApiResponse(assetException.getAssetExceptionCode().getCode(), HttpStatus.OK, assetException.getAssetExceptionCode().getMessage());

    }
}

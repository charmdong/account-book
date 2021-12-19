package com.accountbook.exception.account;

import com.accountbook.api.UserApiController;
import com.accountbook.dto.asset.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class AssetExceptionHandler {

    @ExceptionHandler(AssetException.class)
    public ApiResponse AssetExceptionHandler(AssetException assetException) {

        return new ApiResponse(assetException.getAssetExceptionCode().getCode(), HttpStatus.OK, assetException.getAssetExceptionCode().getMessage());

    }
}

package com.accountbook.exception.account;

import lombok.Getter;

@Getter
public class AssetException extends RuntimeException {

    private AssetExceptionCode assetExceptionCode;

    public AssetException(AssetExceptionCode assetExceptionCode) {
        this.assetExceptionCode = assetExceptionCode;
    }

}

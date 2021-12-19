package com.accountbook.exception.account;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AssetUpdateException extends AssetException {

    public AssetUpdateException(AssetExceptionCode assetExceptionCode) {
        super(assetExceptionCode);
    }
}

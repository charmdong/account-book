package com.accountbook.exception.account;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AssetNotFoundException extends AssetException {

    public AssetNotFoundException(AssetExceptionCode assetExceptionCode) {
        super(assetExceptionCode);
    }

}

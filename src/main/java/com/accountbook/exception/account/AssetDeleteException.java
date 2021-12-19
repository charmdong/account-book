package com.accountbook.exception.account;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AssetDeleteException extends AssetException {

    public AssetDeleteException(AssetExceptionCode assetExceptionCode) {
        super(assetExceptionCode);
    }

}

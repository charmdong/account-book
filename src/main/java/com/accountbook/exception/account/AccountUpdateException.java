package com.accountbook.exception.account;

import com.accountbook.exception.common.CommonExceptionHandler;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AccountUpdateException extends CommonExceptionHandler {

    public AccountUpdateException(int code, String message) {
        super(AccountExceptionCode.FAILED_UPDATE_CODE, AccountExceptionCode.FAILED_UPDATE_MSG);
    }

}

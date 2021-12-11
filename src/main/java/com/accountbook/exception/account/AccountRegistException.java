package com.accountbook.exception.account;

import com.accountbook.exception.common.CommonExceptionHandler;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AccountRegistException extends CommonExceptionHandler {

    public AccountRegistException(int code, String message) {
        super(AccountExceptionCode.FAILED_DELETE_CODE, AccountExceptionCode.FAILED_DELETE_MSG);
    }

}

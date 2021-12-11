package com.accountbook.exception.account;

import com.accountbook.exception.common.CommonExceptionHandler;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AccountNotExistedException extends CommonExceptionHandler {

    public AccountNotExistedException(int code, String message) {
        super(AccountExceptionCode.NOT_EXISTED_ACCOUNT_CODE, AccountExceptionCode.NOT_EXISTED_ACCOUNT_MSG);
    }

}

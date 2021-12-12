package com.accountbook.exception.account;


import com.accountbook.exception.common.CommonException;

/**
 * CategoryException
 *
 * @author donggun
 * @since 2021/12/01
 */
public class AccountNotExistedException extends CommonException {

    public AccountNotExistedException(int code, String message) {
        super(AccountExceptionCode.NOT_EXISTED_ACCOUNT_CODE, AccountExceptionCode.NOT_EXISTED_ACCOUNT_MSG);
    }

}

package com.accountbook.exception.user;

/**
 * SettingNotFoundException
 *
 * @author donggun
 * @since 2022/04/12
 */
public class SettingNotFoundException extends UserException {

    public SettingNotFoundException () {
        super();
    }

    public SettingNotFoundException (String message) {
        super(message);
    }

    public SettingNotFoundException (UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }
}

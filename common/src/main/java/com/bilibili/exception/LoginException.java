package com.bilibili.exception;

import com.bilibili.constants.ErrorMessage;
import com.bilibili.enums.HttpStatusCode;

public class LoginException extends BaseException {
    public LoginException() {
        super(HttpStatusCode.UNAUTHORIZED.getCode(), ErrorMessage.ACCOUNT_OR_PASSWORD_ERROR);
    }
}

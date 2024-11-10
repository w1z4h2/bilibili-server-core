package com.bilibili.exception;

import com.bilibili.constants.ErrorMessage;
import com.bilibili.enums.HttpStatusCode;

public class TokenException extends BaseException{
    public TokenException() {
        super(HttpStatusCode.UNAUTHORIZED.getCode(), ErrorMessage.TOKEN_INVALID);
    }
}

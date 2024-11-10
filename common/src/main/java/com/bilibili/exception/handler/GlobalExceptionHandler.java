package com.bilibili.exception.handler;

import com.bilibili.exception.LoginException;
import com.bilibili.exception.TokenException;
import com.bilibili.result.Result;
import com.bilibili.enums.HttpStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenException.class)
    public Result<Void> handleTokenException(TokenException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public Result<Void> handleLoginException(LoginException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> exception(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());
    }
}

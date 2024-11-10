package com.bilibili.result;

import com.bilibili.enums.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    private final Integer code;
    private final String message;
    private final T data;

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatusCode.OK.getCode(), HttpStatusCode.OK.getMessage(), data);
    }

    public static Result<Void> success() {
        return new Result<>(HttpStatusCode.OK.getCode(), HttpStatusCode.OK.getMessage(), null);
    }

    public static Result<Void> error(Integer code, String message) {
        return new Result<>(code, message);
    }
}

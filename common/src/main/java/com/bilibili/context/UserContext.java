package com.bilibili.context;


import com.bilibili.context.pojo.UserInfo;

public class UserContext {
    private static final ThreadLocal<UserInfo> userContextThreadLocal = new ThreadLocal<>();

    public static UserInfo get() {
        return userContextThreadLocal.get();
    }

    public static void set(UserInfo userInfo) {
        userContextThreadLocal.set(userInfo);
    }

    public static void remove() {
        userContextThreadLocal.remove();
    }
}

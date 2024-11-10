package com.bilibili.context;


public class UserContext {
    private static ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<UserContext>();

    public static UserContext getUserContext() {
        return userContextThreadLocal.get();
    }

    public static void setUserContext(UserContext userContext) {
        userContextThreadLocal.set(userContext);
    }

    public static void removeUserContext() {
        userContextThreadLocal.remove();
    }
}

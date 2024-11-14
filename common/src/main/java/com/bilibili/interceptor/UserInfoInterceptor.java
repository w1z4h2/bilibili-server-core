package com.bilibili.interceptor;

import cn.hutool.json.JSONUtil;
import com.bilibili.context.UserContext;
import com.bilibili.context.pojo.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfoJsonStr = request.getHeader("userInfo");
        UserInfo userInfo = JSONUtil.toBean(userInfoJsonStr, UserInfo.class);
        UserContext.set(userInfo);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}

package com.bilibili.filter;

import com.bilibili.context.pojo.UserInfo;
import com.bilibili.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] EXCLUDED_URLS = {"/user/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isExcludedUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String oldToken = request.getHeader("Authorization").split(" ")[1];

        Claims claims = JwtUtil.validateToken(oldToken);
        UserInfo userInfo = UserInfo.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .build();
        request.setAttribute("userInfo", userInfo);

        String newToken = JwtUtil.refreshToken(oldToken);
        response.setHeader("Authorization", "Bearer " + newToken);

        filterChain.doFilter(request, response);
    }

    private boolean isExcludedUrl(String requestURI) {
        for (String excludedUrl : EXCLUDED_URLS) {
            if (requestURI.equals(excludedUrl)) {
                return true;
            }
        }
        return false;
    }
}

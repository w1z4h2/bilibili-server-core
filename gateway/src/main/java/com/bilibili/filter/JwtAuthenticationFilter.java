package com.bilibili.filter;

import cn.hutool.json.JSONUtil;
import com.bilibili.context.pojo.UserInfo;
import com.bilibili.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter, Ordered {

    private static final String[] EXCLUDED_URLS = {"/user/login"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestURI = exchange.getRequest().getURI().getPath();

        // 排除某些不需要 JWT 校验的路径
        if (isExcludedUrl(requestURI)) {
            return chain.filter(exchange);
        }

        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);

            Claims claims = JwtUtil.validateToken(token);
            if (claims != null) {
                UserInfo userInfo = UserInfo.builder()
                        .userId(Long.valueOf(claims.getSubject()))
                        .build();
                exchange.getRequest().mutate().header("userInfo", JSONUtil.toJsonStr(userInfo)).build();

                String newToken = JwtUtil.refreshToken(token);
                exchange.getResponse().getHeaders().set("Authorization", "Bearer " + newToken);
            }
        }

        return chain.filter(exchange);
    }

    private boolean isExcludedUrl(String requestURI) {
        for (String excludedUrl : EXCLUDED_URLS) {
            if (requestURI.equals(excludedUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}


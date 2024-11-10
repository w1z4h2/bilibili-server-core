package com.bilibili.utils;

import com.bilibili.context.pojo.UserInfo;
import com.bilibili.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    public static String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();

        Field[] fields = UserInfo.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                claims.put(field.getName(), field.get(userInfo));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return createToken(claims, String.valueOf(userInfo.getUserId()));
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new TokenException();
        }
    }

    public static String refreshToken(String token) {
        Claims claims = validateToken(token);
        String userId = claims.getSubject();
        return createToken(claims, userId);
    }
}

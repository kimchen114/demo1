package com.example.ms_demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT生成与解析类。
 */
public class JWTUtil {
    
    private static String secret = "1234567890abcdef";
    
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(defaultDate()).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public static String generateToken(Map<String, Object> claims, Date expire) {
        return Jwts.builder().setClaims(claims).setExpiration(expire).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    /**
     * 根据参数生成Token。
     *
     * @param expire    失效日期。
     * @param keyValues 格式：key1,values1,key2,values2等成对出现。
     * @return 根据失效日期和参数生成的Token。
     */
    public static String generateToken(Date expire, String... keyValues) {
        // 移位后 若keyValue为奇数 最后一个将被忽略
        int total = keyValues.length >> 1;
        Map<String, Object> claims = new HashMap<>(total);
        total <<= 1;
        for (int i = 0; i < total; i++)
            claims.put(keyValues[i++], keyValues[i]);
        return Jwts.builder().setClaims(claims).setExpiration(expire).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public static Claims getTokenClaims(String token) {
        try {
            if (token.startsWith("Bearer "))
                token = token.substring(7);
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RuntimeException("解析Token失败。", e);
        }
    }
    
    public static Claims getClaimsValidate(String token) {
        try {
            if (token.startsWith("Bearer "))
                token = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date()) ? null : claims;
        } catch (Exception e) {
            throw new RuntimeException("解析Token失败。", e);
        }
    }
    
    private static Date defaultDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }
    
}

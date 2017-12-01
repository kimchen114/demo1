package com.example.ms_demo;

import java.util.HashMap;
import java.util.Map;

import com.example.ms_demo.config.JWTUtil;

import io.jsonwebtoken.Claims;

public class Test {

	
	public static void main(String[] args) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("username", "chenjinwei");
		claims.put("userId", "1");
		claims.put("password", "456");
//		
		String token = JWTUtil.generateToken(claims);
		System.err.println(token.length());
//		System.out.println(new BCryptPasswordEncoder().encode("123"));
		Claims claim =JWTUtil.getTokenClaims(token);
		System.out.println(claim.values());
		
		
	}
}

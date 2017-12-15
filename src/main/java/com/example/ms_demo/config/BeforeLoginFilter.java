package com.example.ms_demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ms_demo.domain.UserDetail;
import com.example.ms_demo.service.SysUserService;

import io.jsonwebtoken.Claims;

@Service
public class BeforeLoginFilter extends OncePerRequestFilter {
    
    @Autowired
    private SysUserService sysUserService;
    
   
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if ("token".equals(cookieName)) {
                String token = cookie.getValue();
                Claims claims = JWTUtil.getTokenClaims(token);
                
                System.out.println("***********************");
//                SysUser sysUser = sysUserService.getById(Integer.parseInt(claims.get("userId").toString()));
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_admin_user"));
                authorities.add(new SimpleGrantedAuthority("ROLE_default_user"));
                UserDetail userDetail = new UserDetail(claims.get("username").toString(), "", authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                        userDetail.getPassword(), userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将权限写入本次会话
                SecurityContextHolder.getContext().setAuthentication(authentication);
                break;
            }
        }
        
        // 继续调用 Filter 链
        filterChain.doFilter(request, response);
        
    }
}

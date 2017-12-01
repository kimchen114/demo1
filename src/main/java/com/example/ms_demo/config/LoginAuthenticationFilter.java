package com.example.ms_demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.SysUser;
import com.example.ms_demo.domain.UserDetail;
import com.example.ms_demo.service.SysUserService;

import io.jsonwebtoken.Claims;

//@Service
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
//	@Autowired
	private SysUserService sysUserService;
	
	

	final static Logger logger = LoggerFactory.getLogger(LoginAuthenticationFilter.class);


    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
    	
		super(new AntPathRequestMatcher("/user1/login**", "GET"));
		setAuthenticationManager(authenticationManager);
	}
    
    public LoginAuthenticationFilter() {
    	
    	super(new AntPathRequestMatcher("/user1/login**", "GET"));
    }
    


	@Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }





	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}



	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        
        for (Cookie cookie : cookies) {
        	String cookieName=cookie.getName();
        	if("token".equals(cookieName)) {
        		String token=cookie.getValue();
        		Claims claims = JWTUtil.getTokenClaims(token);
        		SysUser sysUser = sysUserService.getById(Integer.parseInt(claims.get("userId").toString()));
        		List<GrantedAuthority> authorities=new ArrayList<>();
        		authorities.add(new SimpleGrantedAuthority("ROLE_admin_user"));
        		authorities.add(new SimpleGrantedAuthority("ROLE_default_user"));
        		UserDetail userDetail=new UserDetail(sysUser.getUsername(), sysUser.getPassword(),authorities );
        		return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
        	}
		}
        response.sendRedirect("/login");
         throw new UsernameNotFoundException("该用户不存在");
//         return null;
    }
}

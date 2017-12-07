package com.example.ms_demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hujinhu on 2017/6/7.
 */
public class Interceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        // 解决中文乱码
        request.setCharacterEncoding("utf-8");
        // request.
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
//        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        return true;
        
    }
    
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {
        
    }
    
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
        
    }
}

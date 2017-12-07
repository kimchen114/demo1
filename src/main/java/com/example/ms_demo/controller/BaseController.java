package com.example.ms_demo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.ms_demo.domain.UserDetail;

public class BaseController {
    
    public UserDetail getManagerUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = null;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetail = (UserDetail) authentication.getPrincipal();
            return userDetail;
        } else {
            return userDetail;
        }
    }
    
    public String getUserName() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUsername();
    }
    
    public Integer getUserId() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUserId();
    }
    
}

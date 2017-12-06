package com.example.ms_demo.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.SysUser;
import com.example.ms_demo.domain.UserDetail;
import com.example.ms_demo.service.SysUserService;

@Service
public class UserDetailService implements UserDetailsService {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_admin_user"));
        authorities.add(new SimpleGrantedAuthority("ROLE_default_user"));
        UserDetail userDetail = new UserDetail(username, sysUser.getPassword(), authorities);
        userDetail.setUserId(sysUser.getId());
        return userDetail;
    }
    
}

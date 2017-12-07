package com.example.ms_demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用Security注解，例如最常用的@PreAuthorize
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    final static Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
    
    @Autowired
    private UserDetailService UserDetailService;
    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private LoginFailureHandler failureHandler;
    // @Autowired
    // private SysUserService sysUserService;
    @Autowired
    private BeforeLoginFilter beforeLoginFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();  
//        filter.setEncoding("UTF-8");  
//        filter.setForceEncoding(true);  
//        http.addFilterBefore(filter,CsrfFilter.class);  
        http.authorizeRequests()
                // .antMatchers("/login/**","/js/**","/css/**","/img/**","/**").permitAll()
                .antMatchers("/login/**", "/js/**", "/css/**", "/img/**").permitAll().anyRequest().authenticated().and()
                .formLogin().loginPage("/login").passwordParameter("password").usernameParameter("username").permitAll()
                .successHandler(successHandler).failureHandler(failureHandler).and().logout().logoutUrl("/logout")
                .invalidateHttpSession(true).deleteCookies("token").and()
                .addFilterBefore(beforeLoginFilter, UsernamePasswordAuthenticationFilter.class).headers().disable() // 禁用Spring
                                                                                                                    // Security
                                                                                                                    // 自定义
                                                                                                                    // Header,
                                                                                                                    // 主要是禁用
                                                                                                                    // X-Frame-Options
                .csrf().disable().sessionManagement() // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //// 调整为让
                                                                         //// Spring
                                                                         //// Security
                                                                         //// 不创建和使用
                                                                         //// session
        
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication()
        // .withUser("admin")
        // .password("password")
        // .roles("ADMIN");
        auth.userDetailsService(UserDetailService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // @Bean
    // public Filter loginFilter() {
    // LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
    // filter.setSysUserService(sysUserService);
    // return filter;
    // }
}

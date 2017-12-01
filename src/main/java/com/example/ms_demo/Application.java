package com.example.ms_demo;

import java.security.Principal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.ms_demo.config.Interceptor;


@MapperScan("com.example.ms_demo.mapper")
@SpringBootApplication
//@EnableEurekaClient
@RestController
public class Application extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	
	@Bean
	public CharacterEncodingFilter characterEncodingFilter(){
		CharacterEncodingFilter filter=new CharacterEncodingFilter("UTF-8");
		return filter;
	}
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new Interceptor());
	}
	
	
	
}

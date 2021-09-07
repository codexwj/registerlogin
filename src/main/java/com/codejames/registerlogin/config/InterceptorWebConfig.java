package com.codejames.registerlogin.config;

import com.codejames.registerlogin.intercepter.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorWebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/query/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/logout/**");
    }
}


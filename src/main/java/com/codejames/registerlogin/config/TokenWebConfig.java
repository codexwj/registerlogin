package com.codejames.registerlogin.config;

import com.codejames.registerlogin.intercepter.TokenIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenWebConfig implements WebMvcConfigurer {

    //注入拦截器
    @Autowired
    private TokenIntercepter tokenIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenIntercepter).addPathPatterns("/query/**");
        registry.addInterceptor(tokenIntercepter).addPathPatterns("/logout/**");
    }
}

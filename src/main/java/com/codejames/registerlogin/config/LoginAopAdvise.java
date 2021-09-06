package com.codejames.registerlogin.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

//@Component
//@Aspect
public class LoginAopAdvise {
    private Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    //定义一个pointcut,对切入点进行advice
    @Pointcut("@annotation(com.codejames.registerlogin.config.LoginChecker")
    public void pointcut(){
    }


//    public Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();
//
//        String token = request.getCookies().toString();
//    }
}

package com.codejames.registerlogin.aop;

import com.codejames.registerlogin.dto.BaseDto;
import com.codejames.registerlogin.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.codejames.registerlogin.util.SecurityUtil.sortForm;

@Aspect
@Component
public class AOPConfig {

    private final Logger logger =  LoggerFactory.getLogger(AOPConfig.class);

    @Pointcut("@annotation(com.codejames.registerlogin.aop.ValidSignature)")
    void pointcut(){}

    @Around("pointcut()")
    public Object aopAround(ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String signature = request.getHeader("signature");
        String calSign = null;

        for (Object o:joinPoint.getArgs()){
            if (o instanceof BaseDto){
                try {
                    calSign = sortForm((BaseDto) o);
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if(!StringUtils.isEmpty(calSign) && !calSign.equals(signature)){
            logger.info("signature :"+signature+" === vs === sha :"+calSign);

        }
        Object[] objs = joinPoint.getArgs();
        return joinPoint.proceed(objs);
    }

}

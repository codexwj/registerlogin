package com.codejames.registerlogin.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ExpiredAopAdvideDefine {

    private Logger logger = LoggerFactory.getLogger(ExpiredAopAdvideDefine.class);

    @Pointcut("with(SomeService)")
    public void pointcut() {

    }
    public Object methodInvokeExpiredTime(ProceedingJoinPoint pjp) throws Throwable{
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        //begin
        Object retVal = pjp.proceed();

        stopWatch.stop();
        // end

        return retVal;
    }
}

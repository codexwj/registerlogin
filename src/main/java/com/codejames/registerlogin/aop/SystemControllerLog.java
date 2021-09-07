package com.codejames.registerlogin.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Controller
 * 
 * @author Administrator
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
	/**
	 * 描述业务操作 例:Xxx管理-执行Xxx操作
	 * @return
	 */
	String description() default "";
}

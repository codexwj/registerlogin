package com.codejames.registerlogin.config;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

@Documented
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginChecker {
}

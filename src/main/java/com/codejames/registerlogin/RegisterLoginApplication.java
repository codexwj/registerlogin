package com.codejames.registerlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan
//@MapperScan("com.codejames.registerlogin.mapper")
@SpringBootApplication
public class RegisterLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterLoginApplication.class, args);
    }
}

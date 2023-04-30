package com.bowen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bowen
 */
@SpringBootApplication
@MapperScan("com.bowen.mapper")
public class LoginApp {
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class,args);
    }
}
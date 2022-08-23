package com.prestigeding.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "com.prestigeding.user.mapper")
public class RocketMQExampleUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketMQExampleUserApplication.class, args);
    }
}

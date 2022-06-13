package com.qq.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//创建一个edu的springboot启动类
@SpringBootApplication
// nacos注册
@EnableDiscoveryClient
@ComponentScan("com.qq")
@MapperScan("com.qq.aclservice.mapper")
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
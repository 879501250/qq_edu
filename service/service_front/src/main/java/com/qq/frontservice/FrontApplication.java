package com.qq.frontservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//创建一个edu的springboot启动类
@SpringBootApplication
//扫描包下面的组件，添加进spring容器中，主要扫描其他模块的组件，使其也能加入到spring容器中
@ComponentScan(basePackages = {"com.qq"})
//扫描mapper包下的类，不然每个mapper都要加@mapper注解
@MapperScan("com.qq.frontservice.mapper")
// nacos注册
@EnableDiscoveryClient
// feign服务发现
@EnableFeignClients
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}

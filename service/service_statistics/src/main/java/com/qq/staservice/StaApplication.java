package com.qq.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//创建一个edu的springboot启动类
@SpringBootApplication
//扫描包下面的组件，添加进spring容器中，主要扫描其他模块的组件，使其也能加入到spring容器中
@ComponentScan(basePackages = {"com.qq"})
@MapperScan("com.qq.staservice.mapper")
// nacos注册
@EnableDiscoveryClient
// 启动定时任务
@EnableScheduling
// feign服务发现
@EnableFeignClients
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}

package com.qq.eduservice.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//创建一个配置类
@Configuration
@MapperScan("com.qq.eduservice.mapper") //扫描mapper
public class EduConfig {
    //逻辑删除插件
    //MybatisPlus 3.1.1以下版本需要，以上不需要
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

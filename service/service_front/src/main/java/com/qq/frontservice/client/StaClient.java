package com.qq.frontservice.client;

import com.qq.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "service-statistics") // 调用的微服务名
public interface StaClient {
    // 要调用的微服务中的controller方法

    // 为指定日期添加注册人数
    @PutMapping("/staservice/addRegisterNum/{date}")
    Result addRegisterNum(@PathVariable("date") String date);

    // 为指定日期添加登录人数
    @PutMapping("/staservice/addLoginNum/{date}")
    Result addLoginNum(@PathVariable("date") String date);
}

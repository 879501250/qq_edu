package com.qq.ossservice.cliet;

import com.qq.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "service-statistics") // 调用的微服务名
public interface StaClient {
    // 要调用的微服务中的controller方法

    // 为指定日期添加视频浏览量
    @PutMapping("/staservice/addVideoViewNum/{date}")
    Result addVideoViewNum(@PathVariable("date") String date);
}

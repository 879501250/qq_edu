package com.qq.eduservice.client;

import com.qq.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 调用的微服务名，是在模块的配置文件中设置的，后面是熔断机制的回调方法
@FeignClient(name = "service-oss", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    // 要调用的微服务中的controller方法

    // 根据视频id删除阿里云里面的视频文件
    @DeleteMapping("/aliyunservice/video/delVideo/{id}")
    Result delVideo(@PathVariable("id") String id);
}

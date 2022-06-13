package com.qq.eduservice.client;

import com.qq.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// 调用的微服务名，是在模块的配置文件中设置的
@FeignClient(name = "service-statistics")
@Component
public interface StaClient {
    // 要调用的微服务中的controller方法

    // 为指定日期添加文章浏览量
    @PutMapping("/staservice/addArticleViewNum/{date}")
    Result addArticleViewNum(@PathVariable("date") String date);
}

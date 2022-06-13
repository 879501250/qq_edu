package com.qq.eduservice.client;

import com.qq.commonutils.Result;
import org.springframework.stereotype.Component;

/**
 * feign结合Hystrix使用（熔断机制）
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result delVideo(String id) {
        return Result.error().message("time out");
    }
}

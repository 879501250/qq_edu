package com.qq.frontservice.client;

import com.qq.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "service-edu") // 调用的微服务名
public interface EduClient {
    // 要调用的微服务中的controller方法

    // 修改指定课程的评论数量
    @PutMapping("/eduservice/course/updateCourseCommentNum/{id}/{num}")
    Result updateCourseCommentNum(@PathVariable("id") String id,
                                  @PathVariable("num") int num);

    // 修改指定文章的评论数量
    @PutMapping("/eduservice/article/updateCount/{id}/{column}/{count}")
    Result updateCount(@PathVariable String id,
                       @PathVariable String column,
                       @PathVariable int count);
}
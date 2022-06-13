package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduVideo;
import com.qq.eduservice.service.EduVideoService;
import com.qq.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/eduservice/video")
// @CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    // 添加课时信息
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return Result.success();
    }

    // 删除课时信息
    @DeleteMapping("/delVideo/{id}")
    public Result delVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        videoService.delVideo(video);
        return Result.success();
    }

    // 修改课时信息
    @PutMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return Result.success();
    }

    // 查询该课的所有课时信息
    @GetMapping("/getVideos/{id}")
    public Result getVideos(@PathVariable String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> lists = videoService.list(videoQueryWrapper);
        return Result.success().data("videos", lists);
    }

    // 根据课时id查询课时信息
    @GetMapping("/getVideo/{id}")
    public Result getVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return Result.success().data("video", video);
    }
}


package com.qq.ossservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qq.commonutils.DateUtil;
import com.qq.ossservice.cliet.StaClient;
import com.qq.ossservice.service.VideoService;
import com.qq.commonutils.Result;
import com.qq.ossservice.utils.AliyunVodSDKUtils;
import com.qq.ossservice.utils.ConstantPropertiesUtil;
import com.qq.servicebase.exceptionhandler.QqException;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

// @CrossOrigin //跨域
@RestController
@RequestMapping("/aliyunservice/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private StaClient staClient;

    // 向阿里云上传视频文件，并返回地址
    @PostMapping("upload")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {
        String videoId = videoService.uploadVideo(file);
        return Result.success().message("视频上传成功~").data("videoId", videoId);
    }

    // 根据视频id删除阿里云里面的视频文件
    @DeleteMapping("/delVideo/{id}")
    public Result delVideo(@ApiParam(name = "id", value = "云端视频id", required = true)
                           @PathVariable String id) {
        videoService.delVideo(id);
        return Result.success().message("视频删除成功~");
    }

    // 一次性删除多个视频
    @DeleteMapping("delVideo")
    public Result delVideos(@RequestParam("videos") List<String> videos) {
        videoService.delVideos(videos);
        return Result.success();
    }

    // 根据视频id获取播放凭证
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            //获取阿里云存储相关常量
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId,
                    accessKeySecret);
            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //得到播放凭证
            String playAuth = response.getPlayAuth();

            // 播放视频成功后，添加统计数据
            staClient.addVideoViewNum(DateUtil.formatDate(new Date()));

            //返回结果
            return Result.success().message("获取凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new QqException(20001, "视频播放异常~");
        }
    }
}

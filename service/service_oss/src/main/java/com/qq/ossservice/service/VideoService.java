package com.qq.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    // 向阿里云上传视频文件
    String uploadVideo(MultipartFile file);

    // 根据视频id删除阿里云里面的视频文件
    void delVideo(String videoId);

    // 一次性删除多个视频
    void delVideos(List<String> videos);
}

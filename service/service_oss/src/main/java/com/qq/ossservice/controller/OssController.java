package com.qq.ossservice.controller;

import com.qq.ossservice.service.FileService;
import com.qq.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传到OSS
 */
// @CrossOrigin
@RequestMapping("aliyunservice/oss")
@RestController
public class OssController {
    @Autowired
    private FileService fileService;

    // 教师头像或者封面上传
    @PostMapping("/fileoss")
    public Result uploadOssFile(MultipartFile file) {
        String url = fileService.upload(file);
        return Result.success().data("url", url);
    }
}

package com.qq.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qq.ossservice.service.FileService;
import com.qq.ossservice.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.qq.servicebase.exceptionhandler.QqException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    // 上传文件
    @Override
    public String upload(MultipartFile file) {
        //获取阿里云存储相关常量，用工具类取值
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String uploadUrl = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //创建存储空间bucket
            ossClient.createBucket(bucketName);
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            // 将文件按照时间进行分类
            // 构建日期路径：avatar/2019/02/26/文件名，前面的日期文件夹OSS会自动创建
            String filePath = new DateTime().toString("yyyy/MM/dd");
            // 获取文件名称
            String original = file.getOriginalFilename();
            // 为文件名称添加唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String newName = uuid + original;
            String fileUrl = filePath + "/" + newName;
            //上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（fileUrl）。
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            uploadUrl = "http://" + bucketName + "." + endPoint + "/" + fileUrl;
        } catch (IOException e) {
            throw new QqException(20001,"上传文件失败~");
        }
        return uploadUrl;
    }
}

package com.qq.eduservice.service;

import com.qq.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduSubjectService extends IService<EduSubject> {

    // 添加课程分类
    void addSubject(MultipartFile file);

    // 查询所有的课程分类
    List<OneSubject> getAllSubject();

    // 上传文件到指定位置
    String handlerMultipartFile(MultipartFile file,String unid);

    // 下载文件
    void downloadFile(URL theURL, String uploadPath);
}

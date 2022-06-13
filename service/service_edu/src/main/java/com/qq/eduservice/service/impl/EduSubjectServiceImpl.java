package com.qq.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.qq.eduservice.entity.EduSubject;
import com.qq.eduservice.entity.subject.OneSubject;
import com.qq.eduservice.entity.subject.SubjectData;
import com.qq.eduservice.entity.subject.TwoSubject;
import com.qq.eduservice.listener.SubjectExcelListener;
import com.qq.eduservice.mapper.EduSubjectMapper;
import com.qq.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 自动注入监听器
    @Autowired
    private SubjectExcelListener listener;

    @Override
    public void addSubject(MultipartFile file) {
        try {
            // 获取文件输入流
            InputStream in = file.getInputStream();
            // 通过监听器读取excel数据
            EasyExcel.read(in, SubjectData.class, listener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        // 查询所有的课程分类，调用的是本身的方法，下面两个方法等价
        // baseMapper是他继承的父类的父类
        // List<EduSubject> subjects = baseMapper.selectList();
        List<EduSubject> subjects = this.list();
        List<EduSubject> list = new ArrayList<>();
        // 储存所有的一级分类，封装最终数据
        List<OneSubject> oneSubjects = new ArrayList<>();
        for (EduSubject subject : subjects) { // 第一次遍历所有的课程分类
            if ("0".equals(subject.getParentId().trim())) // 如果是一级分类，加入集合
                oneSubjects.add(new OneSubject(subject.getId(), subject.getParentId(), subject.getTitle()));
            else {
                if (!insertChild(oneSubjects, subject)) // 如果没有成功插入二级分类，先保存下来
                    list.add(subject);
            }
        }
        for (EduSubject subject : list) { // 第二次遍历所有未成功插入的二级分类
            insertChild(oneSubjects, subject);
        }
        return oneSubjects;
    }

    @Override
    public void downloadFile(URL theURL, String filePath) {

    }

    @Override
    public String handlerMultipartFile(MultipartFile file,String uploadPath) {
        // 默认地址当前项目根地址
        String directoryPath = EduSubjectServiceImpl.class.getClassLoader().getResource("").getPath();
        System.out.println(directoryPath);
        directoryPath = uploadPath;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileOldName = file.getOriginalFilename();
        int beginIndex = fileOldName.lastIndexOf(".");
        String suffix = fileOldName.substring(beginIndex);
        String newFileName =  fileOldName;
        File upFile = new File(directoryPath + "/" + newFileName);
        OutputStream outputStream = null;
        try {
            byte[] fileByte = file.getBytes();
            outputStream = new FileOutputStream(upFile);
            outputStream.write(fileByte);
            System.out.println("<==  文件写出完成: " + newFileName);
            return newFileName;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return directoryPath + "/" + newFileName;
    }

    /**
     * 遍历所有的一级分类集合，将二级分类插入对应的一级分类
     *
     * @param subjects 一级分类集合
     * @param subject  要插入的二级分类
     * @return
     */
    private boolean insertChild(List<OneSubject> subjects, EduSubject subject) {
        for (OneSubject oneSubject : subjects) {
            if (oneSubject.getId().equals(subject.getParentId())) {
                TwoSubject twoSubject = new TwoSubject();
                // 下面两个方法都可以
                // twoSubject.setId(subject.getId());
                // twoSubject.setTitle(subject.getTitle());
                BeanUtils.copyProperties(subject, twoSubject); // 相同属性复制
                oneSubject.getChildren().add(twoSubject);
                return true;
            }
        }
        return false;
    }


}

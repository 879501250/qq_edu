package com.qq.eduservice.service;

import com.qq.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    // 根据课程id删除课程简介
    boolean delCourseDescription(String id);
}

package com.qq.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.qq.eduservice.entity.frontvo.CourseFrontVo;
import com.qq.eduservice.entity.vo.CourseInfoVo;
import com.qq.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduCourseService extends IService<EduCourse> {

    // 保存课程信息并返回课程id
    String saveCourse(CourseInfoVo info);

    // 根据课程id修改课程信息
    void updateCourse(CourseInfoVo info, String id);

    // 根据传入的课程id，返回课程相关信息
    CoursePublishVo getLastCourse(String id);

    // 根据传入的课程id，最终发布课程
    boolean publishCourse(String id);

    // 根据课程id删除课程
    boolean delCourse(String id);

    // 查询观看人数最多的前4门课程
    List<EduCourse> getHotCourses();

    // 按照前端传来的条件对课程进行分页查询
    Map<String, Object> pageListWeb(Page<EduCourse> coursePage, CourseFrontVo frontVo);

    // 根据课程id查询课程的所有信息
    CourseFrontInfoVo getCourseInfo(String id);

    // 为当前课程浏览量加一
    void addViewCount(String id);

    // 修改指定课程的评论数量
    void updateCourseCommentNum(String id, int num);
}

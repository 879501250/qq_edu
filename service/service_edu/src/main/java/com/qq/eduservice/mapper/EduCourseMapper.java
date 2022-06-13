package com.qq.eduservice.mapper;

import com.qq.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.qq.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    // 根据传入的课程id，返回课程相关信息
    CoursePublishVo getLastCourse(String id);

    // 查询观看人数最多的前4门课程
    List<EduCourse> getHotCourses();

    // 根据课程id查询课程的基本信息
    CourseFrontInfoVo getCourseInfo(String id);
}

package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.eduservice.entity.EduCourse;
import com.qq.eduservice.entity.EduCourseDescription;
import com.qq.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.qq.eduservice.entity.frontvo.CourseFrontVo;
import com.qq.eduservice.entity.vo.CourseInfoVo;
import com.qq.eduservice.entity.vo.CoursePublishVo;
import com.qq.eduservice.mapper.EduCourseMapper;
import com.qq.eduservice.service.EduChapterService;
import com.qq.eduservice.service.EduCourseDescriptionService;
import com.qq.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qq.servicebase.exceptionhandler.QqException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourse(CourseInfoVo info) {
        // 插入课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(info, course); // 相同的属性进行复制
        int i = baseMapper.insert(course);// 调用父类的方法进行插入数据
        if (i <= 0)
            throw new QqException(20001, "插入课程信息错误~");
        String id = course.getId();

        // 插入课程大纲
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(info.getDescription());
        courseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public void updateCourse(CourseInfoVo info, String id) {
        // 根据id修改课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(info, course); // 相同的属性进行复制
        course.setId(id);
        int i = baseMapper.updateById(course);// 调用父类的方法修改数据
        if (i <= 0)
            throw new QqException(20001, "修改课程信息错误~");

        // 插入课程大纲
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(info.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getLastCourse(String id) {
        CoursePublishVo course = baseMapper.getLastCourse(id);
        return course;
    }

    @Override
    public boolean publishCourse(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public boolean delCourse(String id) {
        boolean del1 = courseDescriptionService.delCourseDescription(id);
        boolean del2 = videoService.delVideos(id);
        boolean del3 = chapterService.delChapter(id);
        return del1 && del2 && del3;
    }

    @Override
    public List<EduCourse> getHotCourses() {
        return baseMapper.getHotCourses();
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> coursePage, CourseFrontVo frontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        // 添加查询条件
        if (frontVo != null) {
            if (!StringUtils.isEmpty(frontVo.getSubjectParentId()))
                queryWrapper.eq("subject_parent_id", frontVo.getSubjectParentId());

            if (!StringUtils.isEmpty(frontVo.getSubjectId()))
                queryWrapper.eq("subject_id", frontVo.getSubjectId());

            if (!StringUtils.isEmpty(frontVo.getGmtCreateSort()))
                queryWrapper.orderByDesc("gmt_create");

            if (!StringUtils.isEmpty(frontVo.getPriceSort()))
                queryWrapper.orderByDesc("price");

            if (!StringUtils.isEmpty(frontVo.getViewCountSort()))
                queryWrapper.orderByDesc("view_count");

            if (!StringUtils.isEmpty(frontVo.getCourseName()))
                queryWrapper.like("title", "%" + frontVo.getCourseName() + "%");
        }

        // 把分页数据封装的coursePage
        baseMapper.selectPage(coursePage, queryWrapper);
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        // 将数据取出来放到map集合中
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseFrontInfoVo getCourseInfo(String id) {
        CourseFrontInfoVo infoVo = baseMapper.getCourseInfo(id);
        return infoVo;
    }

    @Override
    public void addViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

    @Override
    public void updateCourseCommentNum(String id, int num) {
        // 先查出当前课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        // 修改评论数量
        eduCourse.setCommentNum(eduCourse.getCommentNum() + num);
        // 数据库修改
        baseMapper.updateById(eduCourse);
    }
}

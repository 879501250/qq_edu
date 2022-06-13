package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.eduservice.entity.EduCourse;
import com.qq.eduservice.entity.chapter.Chapter;
import com.qq.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.qq.eduservice.entity.frontvo.CourseFrontVo;
import com.qq.eduservice.entity.vo.CourseInfoVo;
import com.qq.eduservice.entity.vo.CoursePublishVo;
import com.qq.eduservice.entity.vo.CourseQuery;
import com.qq.eduservice.service.EduChapterService;
import com.qq.eduservice.service.EduCourseDescriptionService;
import com.qq.eduservice.service.EduCourseService;
import com.qq.commonutils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/eduservice/course")
// @CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;

    // 将封装好了的课程信息保存下来（传入的json数据）
    @PostMapping("/saveCourse")
    public Result saveCourse(@RequestBody CourseInfoVo info) {
        String id = courseService.saveCourse(info);
        return Result.success().data("id", id);
    }

    // 根据课程id修改课程信息
    @PutMapping("/updateCourse/{id}")
    public Result updateCourse(@RequestBody CourseInfoVo info,
                               @PathVariable String id) {
        courseService.updateCourse(info, id);
        return Result.success().data("id", id);
    }

    // 根据课程id获取课程信息
    @GetMapping("/getCourse/{id}")
    public Result getCourse(@PathVariable String id) {
        CourseInfoVo info = new CourseInfoVo();
        BeanUtils.copyProperties(courseService.getById(id), info);
        BeanUtils.copyProperties(courseDescriptionService.getById(id), info);
        return Result.success().data("course", info).data("subjectId", info.getSubjectId());
    }

    // 根据传入的课程id，返回即将发布的课程相关信息
    @GetMapping("/getLastCourse/{id}")
    public Result getLastCourse(@PathVariable String id) {
        CoursePublishVo course = courseService.getLastCourse(id);
        return Result.success().data("course", course);
    }

    // 根据传入的课程id，最终发布课程
    @PutMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable String id) {
        if (courseService.publishCourse(id))
            return Result.success();
        else
            return Result.error();
    }

    /**
     * 分页查询课程
     *
     * @param current 当前页
     * @param count   每页记录数
     * @return
     */
    @GetMapping("/findAll/{current}/{count}")
    public Result findAllCourseByPage(@PathVariable("current") int current,
                                      @PathVariable("count") int count) {
        //创造page对象
        Page<EduCourse> coursePage = new Page<>(current, count);
        //调用方法实现分页
        //调用方法时，底层已经封装了，把分页所有数据封装到teacherPage对象里面了
        courseService.page(coursePage);

        long total = coursePage.getTotal(); //总记录数
        List<EduCourse> courses = coursePage.getRecords(); //当前页面数据list集合
        return Result.success().data("total", total).data("courses", courses);
    }

    /**
     * 按条件分页查询课程（后端）
     *
     * @param conditions 查询的条件，可以为空
     * @param current    当前页
     * @param count      每页记录数
     * @return
     */
    @PostMapping("/findAll/{current}/{count}")
    public Result findAllCourseByConditions
    (@RequestBody(required = false) CourseQuery conditions,
     @PathVariable("current") int current,
     @PathVariable("count") int count) {
        //创造page对象
        Page<EduCourse> coursePage = new Page<>(current, count);

        //创建动态sql语句
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (conditions != null) {
            //下面是数据库字段名！！！不是属性名
            if (!StringUtils.isEmpty(conditions.getTeacherId())) //教师的id
                queryWrapper.eq("teacher_id", conditions.getTeacherId());
            if (!StringUtils.isEmpty(conditions.getSubjectId())) //课程二级分类类别
                queryWrapper.eq("subject_id", conditions.getSubjectId());
            if (!StringUtils.isEmpty(conditions.getSubjectParentId())) //课程一级分类类别
                queryWrapper.eq("subject_parent_id", conditions.getSubjectParentId());
            if ("Normal".equals(conditions.getStatus()) || "Draft".equals(conditions.getStatus())) //课程是否发布
                queryWrapper.eq("status", conditions.getStatus());
        }

        //调用方法实现分页
        //调用方法时，底层已经封装了，把分页所有数据封装到teacherPage对象里面了
        courseService.page(coursePage, queryWrapper);

        long total = coursePage.getTotal();
        List<EduCourse> courses = coursePage.getRecords();
        return Result.success().data("total", total).data("courses", courses);
    }

    // 根据课程id删除课程
    @DeleteMapping("/delCourse/{id}")
    public Result delCourse(@PathVariable String id) {
        boolean result = courseService.delCourse(id);
        if (result) {
            if (courseService.removeById(id))
                return Result.success();
        }
        return Result.error().message("删除课程失败~");
    }

    // 查询观看人数最多的前4门课程
    @GetMapping("/getHotCourses")
    public Result getHotCourses() {
        return Result.success().data("courses", courseService.getHotCourses());
    }

    /**
     * 按照条件分页查询课程
     *
     * @param current 当前页
     * @param count   每页记录数
     * @param frontVo 前端传来的查询条件
     * @return
     */
    @PostMapping("/getFrontCourseList/{current}/{count}")
    public Result pageListWeb(@PathVariable("current") int current,
                              @PathVariable("count") int count,
                              @RequestBody(required = false) CourseFrontVo frontVo) {
        //创造page对象
        Page<EduCourse> coursePage = new Page<>(current, count);
        //调用方法实现分页
        Map<String, Object> map = courseService.pageListWeb(coursePage, frontVo);

        return Result.success().data(map);
    }

    // 根据课程id查询课程的所有信息
    @GetMapping("/getCourseInfo/{id}")
    public Result getCourseInfo(@PathVariable String id) {
        // 查询课程基本信息
        CourseFrontInfoVo infoVo = courseService.getCourseInfo(id);
        // 查询课程章节信息
        List<Chapter> chapters = chapterService.getChapters(id);
        return Result.success().data("course", infoVo).data("chapters", chapters);
    }

    // 为当前课程浏览量加一
    @PutMapping("/addViewCount/{id}")
    public Result addViewCount(@PathVariable String id) {
        courseService.addViewCount(id);
        return Result.success();
    }

    // 修改指定课程的评论数量
    @PutMapping("/updateCourseCommentNum/{id}/{num}")
    Result updateCourseCommentNum(@PathVariable("id") String id,
                                  @PathVariable("num") int num) {
        courseService.updateCourseCommentNum(id, num);
        return Result.success();
    }
}


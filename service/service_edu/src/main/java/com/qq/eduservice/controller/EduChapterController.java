package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduChapter;
import com.qq.eduservice.entity.EduVideo;
import com.qq.eduservice.entity.chapter.Chapter;
import com.qq.eduservice.service.EduChapterService;
import com.qq.eduservice.service.EduVideoService;
import com.qq.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qq.servicebase.exceptionhandler.QqException;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/eduservice/chapter")
// @CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    // 添加章节信息
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return Result.success();
    }

    /**
     * 删除章节，根据传过来的章节id查询，若该章节包含课时信息，则不删除，发出提示
     */
    @DeleteMapping("/delChapter/{id}")
    public Result delChapter(@PathVariable String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        int count = videoService.count(videoQueryWrapper); // 获取查询结果的数量
        if (count > 0) // 当当前章节存在课时时，报错
            throw new QqException(20001, "删除课时信息失败~");
        chapterService.removeById(id);
        return Result.success();
    }

    // 修改章节信息
    @PutMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return Result.success();
    }

    // 根据课程id查询所有的章节信息
    @GetMapping("/getChapters/{id}")
    public Result getChapters(@PathVariable String id) {
        List<Chapter> lists = chapterService.getChapters(id);
        return Result.success().data("chapters", lists);
    }

    // 根据章节id查询章节信息
    @GetMapping("/getChapter/{id}")
    public Result getChapter(@PathVariable String id) {
        EduChapter chapter = chapterService.getById(id);
        return Result.success().data("chapter", chapter);
    }

}


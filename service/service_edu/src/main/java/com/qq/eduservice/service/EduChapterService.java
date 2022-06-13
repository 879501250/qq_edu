package com.qq.eduservice.service;

import com.qq.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.eduservice.entity.chapter.Chapter;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduChapterService extends IService<EduChapter> {

    // 根据课程id查询所有的章节信息
    List<Chapter> getChapters(String id);

    // 根据课程id删除课程所有章节
    boolean delChapter(String id);
}

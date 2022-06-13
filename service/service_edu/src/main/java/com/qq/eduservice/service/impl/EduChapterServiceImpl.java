package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduChapter;
import com.qq.eduservice.entity.EduVideo;
import com.qq.eduservice.entity.chapter.Chapter;
import com.qq.eduservice.entity.chapter.Video;
import com.qq.eduservice.mapper.EduChapterMapper;
import com.qq.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoservice;

    @Override
    public List<Chapter> getChapters(String id) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        // 先查出该课程所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterQueryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = this.list(chapterQueryWrapper);
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoQueryWrapper.orderByAsc("sort");
        List<EduVideo> videos = videoservice.list(videoQueryWrapper);
        // 遍历每个章节查询所有的课时信息
        for (EduChapter eduChapter : eduChapters) {
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter, chapter);
            // 将所有的课时信息加入到对应章节里
            if (videos.size() != 0) {
                ArrayList<EduVideo> list = new ArrayList<>();
                for (EduVideo eduVideo : videos) {
                    if(!eduVideo.getChapterId().equals(eduChapter.getId()))
                        continue;
                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo, video);
                    chapter.getChildren().add(video);
                    list.add(eduVideo);
                }
                videos.removeAll(list);
            }
            chapters.add(chapter);
        }
        return chapters;
    }

    @Override
    public boolean delChapter(String id) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count >= 0;
    }
}

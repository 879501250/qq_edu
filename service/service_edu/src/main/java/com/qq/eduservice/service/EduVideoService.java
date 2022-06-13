package com.qq.eduservice.service;

import com.qq.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface EduVideoService extends IService<EduVideo> {

    // 查询所有的课时信息
    List<EduVideo> getVideos(String id);

    // 根据课程id删除课程所有课时
    boolean delVideos(String id);

    // 根据传入的课时删除课时
    boolean delVideo(EduVideo video);
}

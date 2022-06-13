package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.client.VodClient;
import com.qq.eduservice.entity.EduVideo;
import com.qq.eduservice.mapper.EduVideoMapper;
import com.qq.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public List<EduVideo> getVideos(String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> videos = this.list(videoQueryWrapper);
        return videos;
    }

    /**
     * 还可以调用oss中delvideos方法一次性批量删除阿里云视频
     * @param id
     * @return
     */
    @Override
    public boolean delVideos(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        // 查出所有的课时信息
        List<EduVideo> videos = baseMapper.selectList(queryWrapper);
        // 遍历每个课时信息，依次删除每个课时
        for(EduVideo video:videos)
            if(!delVideo(video))
                return false;
        return true;
    }

    @Override
    public boolean delVideo(EduVideo video) {
        // 先删除阿里云中的视频
        if(!StringUtils.isEmpty(video.getVideoSourceId()))
            vodClient.delVideo(video.getVideoSourceId());
        boolean b = this.removeById(video);
        return b;
    }
}

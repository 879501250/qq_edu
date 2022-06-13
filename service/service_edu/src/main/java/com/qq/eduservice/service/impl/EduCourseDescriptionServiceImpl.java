package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduCourseDescription;
import com.qq.eduservice.mapper.EduCourseDescriptionMapper;
import com.qq.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public boolean delCourseDescription(String id) {
        QueryWrapper<EduCourseDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count >= 0;
    }
}

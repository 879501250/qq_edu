package com.qq.frontservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.frontservice.entity.EduComment;
import com.qq.frontservice.mapper.EduCommentMapper;
import com.qq.frontservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-26
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> getCourseComments(Page<EduComment> commentPage, String id) {
        // 添加查询条件
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);

        // 调用方法时，底层已经封装了，把分页所有数据封装到commentPage对象里面了
        // 把分页数据封装的coursePage
        baseMapper.selectPage(commentPage,queryWrapper);
        List<EduComment> records = commentPage.getRecords();
        long current = commentPage.getCurrent();
        long pages = commentPage.getPages();
        long size = commentPage.getSize();
        long total = commentPage.getTotal();
        boolean hasNext = commentPage.hasNext();
        boolean hasPrevious = commentPage.hasPrevious();
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
}

package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.commonutils.DateUtil;
import com.qq.eduservice.client.StaClient;
import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduCourse;
import com.qq.eduservice.entity.frontvo.ArticleFrontVo;
import com.qq.eduservice.mapper.EduArticleMapper;
import com.qq.eduservice.service.EduArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.servicebase.exceptionhandler.QqException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
@Service
public class EduArticleServiceImpl extends ServiceImpl<EduArticleMapper, EduArticle> implements EduArticleService {

    @Autowired
    private StaClient staClient;

    @Override
    public void updateCount(String id, String column, int count) {
        // 先查出当前课程信息
        EduArticle article = baseMapper.selectById(id);
        switch (column){
            case "like":  // 修改点赞数量
                article.setLikeCount(article.getLikeCount()+count);
                break;
            case "view": // 修改浏览量
                article.setViewCount(article.getViewCount()+count);
                // 增加统计的文章浏览量
                staClient.addArticleViewNum(DateUtil.formatDate(new Date()));
                break;
            case "comment": // 修改评论数量
                article.setCommentCount(article.getCommentCount()+count);
                break;
        }
        // 数据库修改
        baseMapper.updateById(article);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduArticle> articlePage, ArticleFrontVo frontVo) {
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        // 添加查询条件
        if (frontVo != null) {
            if (!StringUtils.isEmpty(frontVo.getGmtModifiedSort()))
                queryWrapper.orderByDesc("gmt_modified");

            if (!StringUtils.isEmpty(frontVo.getCommentCountSort()))
                queryWrapper.orderByDesc("comment_count");

            if (!StringUtils.isEmpty(frontVo.getViewCountSort()))
                queryWrapper.orderByDesc("view_count");

            if (!StringUtils.isEmpty(frontVo.getLikeCountSort()))
                queryWrapper.orderByDesc("like_count");

            if (!StringUtils.isEmpty(frontVo.getTitle()))
                queryWrapper.like("title", frontVo.getTitle());
        }

        // 把分页数据封装到articlePage
        baseMapper.selectPage(articlePage, queryWrapper);
        List<EduArticle> records = articlePage.getRecords();
        long current = articlePage.getCurrent();
        long pages = articlePage.getPages();
        long size = articlePage.getSize();
        long total = articlePage.getTotal();
        boolean hasNext = articlePage.hasNext();
        boolean hasPrevious = articlePage.hasPrevious();
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
    public List<EduArticle> getHotArticles() {
        return baseMapper.getHotArticles();
    }
}

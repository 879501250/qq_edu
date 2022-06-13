package com.qq.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.eduservice.entity.EduArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.eduservice.entity.frontvo.ArticleFrontVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
public interface EduArticleService extends IService<EduArticle> {

    // 根据文章id及列名修改对应数据
    void updateCount(String id, String column, int count);

    // 按照条件分页查询文章（前端）
    Map<String, Object> pageListWeb(Page<EduArticle> articlePage, ArticleFrontVo frontVo);

    // 获取浏览量前4高的文章
    List<EduArticle> getHotArticles();
}

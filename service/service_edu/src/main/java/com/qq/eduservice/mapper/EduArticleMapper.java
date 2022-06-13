package com.qq.eduservice.mapper;

import com.qq.eduservice.entity.EduArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
public interface EduArticleMapper extends BaseMapper<EduArticle> {

    // 获取浏览量前4高的文章
    List<EduArticle> getHotArticles();
}

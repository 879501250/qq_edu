package com.qq.eduservice.mapper;

import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduArticleUser;
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
public interface EduArticleUserMapper extends BaseMapper<EduArticleUser> {

    // 获取某个用户所有点赞的文章
    List<String> getArticles(String id);

}

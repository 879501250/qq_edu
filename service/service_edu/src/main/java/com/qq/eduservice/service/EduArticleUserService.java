package com.qq.eduservice.service;

import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduArticleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
public interface EduArticleUserService extends IService<EduArticleUser> {

    // 获取某个用户所有点赞的文章
    List<String> getArticles(String id);

    // 判断某个用户是否点赞了某篇文章
    EduArticleUser isLike(String articleId, String userId);
}

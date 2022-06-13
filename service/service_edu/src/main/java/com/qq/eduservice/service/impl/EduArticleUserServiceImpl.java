package com.qq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduArticleUser;
import com.qq.eduservice.mapper.EduArticleUserMapper;
import com.qq.eduservice.service.EduArticleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
@Service
public class EduArticleUserServiceImpl extends ServiceImpl<EduArticleUserMapper, EduArticleUser> implements EduArticleUserService {

    @Override
    public List<String> getArticles(String id) {
        List<String> articles = baseMapper.getArticles(id);
        return articles;
    }

    @Override
    public EduArticleUser isLike(String articleId, String userId) {
        QueryWrapper<EduArticleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);
        queryWrapper.eq("user_id",userId);
        return baseMapper.selectOne(queryWrapper);
    }
}

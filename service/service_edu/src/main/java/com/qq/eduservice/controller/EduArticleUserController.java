package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.commonutils.Result;
import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduArticleUser;
import com.qq.eduservice.service.EduArticleService;
import com.qq.eduservice.service.EduArticleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/eduservice/articleUser")
public class EduArticleUserController {
    @Autowired
    private EduArticleUserService articleUserService;
    @Autowired
    private EduArticleService articleService;

    // 获取某个用户所有点赞的文章
    @GetMapping("/getArticles/{id}")
    public Result getArticles(@PathVariable String id) {
        List<String> articles = articleUserService.getArticles(id);
        return Result.success().data("articles", articles);
    }

    // 判断某个用户是否点赞了某篇文章
    @GetMapping("/isLike/{articleId}/{userId}")
    public Result isLike(@PathVariable String articleId,
                         @PathVariable String userId) {
        EduArticleUser article = articleUserService.isLike(articleId, userId);
        return Result.success().data("article", article);
    }

    // 点赞
    @PostMapping("/like")
    public Result like(@RequestBody EduArticleUser eduArticleUser) {
        articleUserService.save(eduArticleUser);
        // 增加点赞数
        articleService.updateCount(eduArticleUser.getArticleId(),"like",1);
        return Result.success();
    }

    // 取消点赞
    @DeleteMapping("/cancelLike/{articleId}/{userId}")
    public Result cancelLike(@PathVariable String articleId,
                             @PathVariable String userId) {
        QueryWrapper<EduArticleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("user_id", userId);
        articleUserService.remove(queryWrapper);
        // 减少点赞数
        articleService.updateCount(articleId,"like",-1);
        return Result.success();
    }
}


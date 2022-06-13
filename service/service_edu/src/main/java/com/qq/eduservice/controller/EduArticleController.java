package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.commonutils.Result;
import com.qq.eduservice.entity.EduArticle;
import com.qq.eduservice.entity.EduCourse;
import com.qq.eduservice.entity.chapter.Chapter;
import com.qq.eduservice.entity.frontvo.ArticleFrontVo;
import com.qq.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.qq.eduservice.entity.frontvo.CourseFrontVo;
import com.qq.eduservice.entity.vo.CourseInfoVo;
import com.qq.eduservice.service.EduArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/eduservice/article")
public class EduArticleController {
    @Autowired
    private EduArticleService articleService;

    /**
     * 分页按条件查询文章（后端）
     *
     * @param current 当前页
     * @param count   每页记录数
     * @param title   模糊查询的题目
     * @return
     */
    @GetMapping("/findAllArticleByPage/{current}/{count}")
    public Result findAllArticleByPage(@PathVariable("current") int current,
                                       @PathVariable("count") int count,
                                       @RequestParam("title") String title) {
        //创造page对象
        Page<EduArticle> articlePage = new Page<>(current, count);
        //调用方法实现分页
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        if (title != null)
            queryWrapper.like("title", title);
        //调用方法时，底层已经封装了，把分页所有数据封装到teacherPage对象里面了
        articleService.page(articlePage, queryWrapper);

        long total = articlePage.getTotal(); //总记录数
        List<EduArticle> articles = articlePage.getRecords(); //当前页面数据list集合
        return Result.success().data("total", total).data("articles", articles);
    }

    /**
     * 按照条件分页查询文章（前端）
     *
     * @param current 当前页
     * @param count   每页记录数
     * @param frontVo 前端传来的查询条件
     * @return
     */
    @PostMapping("/pageListWeb/{current}/{count}")
    public Result pageListWeb(@PathVariable("current") int current,
                              @PathVariable("count") int count,
                              @RequestBody(required = false) ArticleFrontVo frontVo) {
        //创造page对象
        Page<EduArticle> articlePage = new Page<>(current, count);
        //调用方法实现分页
        Map<String, Object> map = articleService.pageListWeb(articlePage, frontVo);

        return Result.success().data(map);
    }

    // 根据文章id删除文章
    @DeleteMapping("/delArticle/{id}")
    public Result delArticle(@PathVariable String id) {
        boolean result = articleService.removeById(id);
        if (result)
            return Result.success();
        return Result.error().message("删除文章失败~");
    }

    // 根据文章id查询文章的所有信息
    @GetMapping("/getArticleInfo/{id}")
    public Result getArticleInfo(@PathVariable String id) {
        EduArticle article = articleService.getById(id);
        return Result.success().data("article", article);
    }

    // 添加文章
    @PostMapping("/saveArticle")
    public Result saveArticle(@RequestBody EduArticle article) {
        articleService.save(article);
        return Result.success();
    }

    // 根据文章id修改文章信息
    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody EduArticle article) {
        articleService.updateById(article);
        return Result.success();
    }

    // 根据文章id及列名修改对应数据
    @PutMapping("/updateCount/{id}/{column}/{count}")
    public Result updateCount(@PathVariable String id,
                              @PathVariable String column,
                              @PathVariable int count) {
        articleService.updateCount(id, column, count);
        return Result.success();
    }

    // 获取浏览量前4高的文章
    @GetMapping("/getHotArticles")
    public Result getHotArticles() {
        List<EduArticle> articles = articleService.getHotArticles();
        return Result.success().data("articles", articles);
    }
}


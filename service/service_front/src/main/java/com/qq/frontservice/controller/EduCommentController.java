package com.qq.frontservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.commonutils.Result;
import com.qq.frontservice.client.EduClient;
import com.qq.frontservice.entity.EduComment;
import com.qq.frontservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/frontservice/comment")
// @CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private EduClient eduClient;

    // 根据课程id分页查询包括的所有评论
    @GetMapping("/getCourseComments/{current}/{count}/{id}")
    public Result getCourseComments(@PathVariable("current") int current,
                                    @PathVariable("count") int count,
                                    @PathVariable("id") String id) {
        Page<EduComment> commentPage = new Page<>(current, count);
        Map<String, Object> map = commentService.getCourseComments(commentPage, id);
        return Result.success().data(map);
    }

    // 根据评论id，删除评论
    @DeleteMapping("/delComment/{parentId}/{id}/{type}")
    public Result delComment(@PathVariable String parentId,
                             @PathVariable String id,
                             @PathVariable int type) {
        // 删除评论
        commentService.removeById(id);
        if (type == 0)
            // 删除课程评论数
            eduClient.updateCourseCommentNum(parentId, -1);
        else
            // 删除文章评论数
            eduClient.updateCount(parentId, "comment", -1);
        return Result.success();
    }

    // 添加评论
    @PostMapping("/addComment")
    public Result addComment(@RequestBody EduComment comment) {
        // 添加评论
        commentService.save(comment);
        if (comment.getType() == 0)
            // 添加课程评论数
            eduClient.updateCourseCommentNum(comment.getParentId(), 1);
        else
            // 添加文章评论数
            eduClient.updateCount(comment.getParentId(), "comment", 1);
        return Result.success();
    }

}


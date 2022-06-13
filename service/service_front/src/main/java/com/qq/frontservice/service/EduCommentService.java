package com.qq.frontservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.frontservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-26
 */
public interface EduCommentService extends IService<EduComment> {
    // 根据课程id分页查询包括的所有评论
    Map<String, Object> getCourseComments(Page<EduComment> commentPage, String id);

}

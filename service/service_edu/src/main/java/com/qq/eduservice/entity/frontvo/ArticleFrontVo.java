package com.qq.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 封装一个前台的文章查询条件
@Data
public class ArticleFrontVo {
    @ApiModelProperty(value = "模糊查询文章的标题")
    private String title;
    @ApiModelProperty(value = "更新时间排序")
    private String gmtModifiedSort;
    @ApiModelProperty(value = "点赞数排序")
    private String likeCountSort;
    @ApiModelProperty(value = "评论数排序")
    private String commentCountSort;
    @ApiModelProperty(value = "浏览量排序")
    private String viewCountSort;
}

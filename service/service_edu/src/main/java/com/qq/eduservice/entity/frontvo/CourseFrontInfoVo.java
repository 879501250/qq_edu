package com.qq.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="前端课程信息", description="网站课程详情页需要的相关字段")
@Data
public class CourseFrontInfoVo {

    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;
    @ApiModelProperty(value = "课程简介")
    private String description;
    @ApiModelProperty(value = "一级分类名称")
    private String subjectLevelOne;
    @ApiModelProperty(value = "二级分类名称")
    private String subjectLevelTwo;
}

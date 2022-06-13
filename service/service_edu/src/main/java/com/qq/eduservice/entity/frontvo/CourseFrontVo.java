package com.qq.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 封装一个前台的课程查询条件
@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;
    @ApiModelProperty(value = "价格排序")
    private String priceSort;
    @ApiModelProperty(value = "观看数量排序")
    private String viewCountSort;
    @ApiModelProperty(value = "模糊查询的课程名称")
    private String courseName;
}

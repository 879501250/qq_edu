package com.qq.eduservice.entity.vo;

import lombok.Data;

/**
 * 封装一个资料的查询条件
 */
@Data
public class MaterialQuery {
    // 模糊查询题目
    private String title;
    // 所属资源类型，0表示网站，1表示app，2表示全部
    private Integer type;
    // 是否是国内资源，0表示国内，1表示国外，2表示全部
    private Integer isAbroad;
}

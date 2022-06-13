package com.qq.eduservice.entity.subject;

import lombok.Data;

/**
 * 创建课程二级分类
 */
@Data
public class TwoSubject {
    private String id;
    private String title;
    private String parentId;
}

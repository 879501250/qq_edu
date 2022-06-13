package com.qq.eduservice.entity.vo;

import lombok.Data;

// 封装了一个课程查询条件类
@Data
public class CourseQuery {
    private String teacherId;
    private String subjectId;
    private String subjectParentId;
    private String status;
}

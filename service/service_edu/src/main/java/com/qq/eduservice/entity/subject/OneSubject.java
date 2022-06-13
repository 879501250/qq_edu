package com.qq.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建课程一级分类
 */
@Data
public class OneSubject {
    private String id;
    private String parentId;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();

    public OneSubject(){}

    public OneSubject(String id, String parentId, String title) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
    }
}

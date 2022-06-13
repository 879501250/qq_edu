package com.qq.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一个章节信息类，包含了该章的课时信息
 */
@Data
public class Chapter {
    private String id;
    private String title;
    private Integer sort;
    private List<Video> children = new ArrayList<>();
}

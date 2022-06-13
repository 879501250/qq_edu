package com.qq.eduservice.entity.chapter;

import lombok.Data;

/**
 * 封装一个课时信息类
 */
@Data
public class Video {

    private String id;
    private String title;
    private Integer isFree;
    private Integer sort;
    private String videoSourceId;
}

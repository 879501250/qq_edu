package com.qq.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author qq
 * @since 2021-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduArticle对象", description="")
public class EduArticle implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    private String author;

    private String content;

    private String introduction;

    private Long viewCount;

    private Integer commentCount;

    private Integer likeCount;
    @TableLogic //该字段启用逻辑删除
    private Integer isDeleted;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT)//配合插件达到创建时自动填充的效果
    private Date gmtCreate;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT_UPDATE)//配合插件达到创建和修改时自动填充的效果
    private Date gmtModified;


}

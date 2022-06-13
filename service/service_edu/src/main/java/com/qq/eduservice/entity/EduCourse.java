package com.qq.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduCourse对象", description="")
public class EduCourse implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String subjectId;

    private String subjectParentId;

    private String title;

    private Double price;

    private Integer lessonNum;

    private Integer commentNum;

    private String cover;

    private Long viewCount;

    private Long version;

    private String status;
    @TableLogic //该字段启用逻辑删除
    private Integer isDeleted;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT)//配合插件达到创建时自动填充的效果
    private Date gmtCreate;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT_UPDATE)//配合插件达到创建和修改时自动填充的效果
    private Date gmtModified;

}

package com.qq.frontservice.entity;

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
 * @since 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduComment对象", description="")
public class EduComment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String parentId;

    private String userId;

    private String nickname;

    private String avatar;

    private String content;

    private Integer type;
    @TableLogic //该字段启用逻辑删除
    private Integer isDeleted;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT)//配合插件达到创建时自动填充的效果
    private Date gmtCreate;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT_UPDATE)//配合插件达到创建和修改时自动填充的效果
    private Date gmtModified;


}

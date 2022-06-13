package com.qq.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="EduSubject对象", description="")
public class EduSubject implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR) //主键自增长策略
    private String id;

    private String title;

    private String parentId;

    private Integer sort;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT)//配合插件达到创建时自动填充的效果
    private Date gmtCreate;
    @DateTimeFormat
    @TableField(fill = FieldFill.INSERT_UPDATE)//配合插件达到创建和修改时自动填充的效果
    private Date gmtModified;


}

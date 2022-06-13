package com.qq.eduservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="EduArticleUser对象", description="")
public class EduArticleUser implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String articleId;

    private String userId;


}

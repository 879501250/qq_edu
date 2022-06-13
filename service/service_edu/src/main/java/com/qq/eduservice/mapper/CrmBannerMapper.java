package com.qq.eduservice.mapper;

import com.qq.eduservice.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {

    // 查询排序在前一项的幻灯片
    CrmBanner getForwardBanner(String sort);

    // 查询排序在后一项的幻灯片
    CrmBanner getBackwardBanner(String sort);

    // 将指定id的幻灯片排序修改为指定的sort
    CrmBanner updateBannerSort(@Param("id") String id, @Param("sort") String sort);

    // 获取所有的幻灯片数量
    int getCount();
}

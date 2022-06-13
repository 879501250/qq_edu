package com.qq.frontservice.mapper;

import com.qq.frontservice.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {

    // 获取排在前5位且未删除的幻灯片
    List<CrmBanner> getBanners();

}

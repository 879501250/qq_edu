package com.qq.frontservice.service;

import com.qq.frontservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 获取排在前5位且未删除的幻灯片
    List<CrmBanner> getBanners();
}

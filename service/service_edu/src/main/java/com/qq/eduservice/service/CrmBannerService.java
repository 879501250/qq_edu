package com.qq.eduservice.service;

import com.qq.eduservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 往前移Banner
    void forwardBanner(String id, String sort);

    // 往后移Banner
    void backwardBanner(String id, String sort);

    // 新增Banner
    void add(CrmBanner banner);
}

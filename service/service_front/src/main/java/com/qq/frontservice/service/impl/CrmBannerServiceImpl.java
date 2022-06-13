package com.qq.frontservice.service.impl;

import com.qq.frontservice.entity.CrmBanner;
import com.qq.frontservice.mapper.CrmBannerMapper;
import com.qq.frontservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> getBanners() {
        return baseMapper.getBanners();
    }
}

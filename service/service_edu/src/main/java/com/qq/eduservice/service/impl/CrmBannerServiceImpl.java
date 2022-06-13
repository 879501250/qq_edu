package com.qq.eduservice.service.impl;

import com.qq.eduservice.entity.CrmBanner;
import com.qq.eduservice.mapper.CrmBannerMapper;
import com.qq.eduservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public void forwardBanner(String id, String sort) {
        // 查询排序在前一项的幻灯片
        CrmBanner forwardBanner = baseMapper.getForwardBanner(sort);
        // 修改当前幻灯片的排序
        baseMapper.updateBannerSort(id, forwardBanner.getSort().toString());
        // 修改前一项幻灯片的排序
        baseMapper.updateBannerSort(forwardBanner.getId(), sort);
    }

    @Override
    public void backwardBanner(String id, String sort) {
        // 查询排序在后一项的幻灯片
        CrmBanner backBanner = baseMapper.getBackwardBanner(sort);
        // 修改当前幻灯片的排序
        baseMapper.updateBannerSort(id, backBanner.getSort().toString());
        // 修改前一项幻灯片的排序
        baseMapper.updateBannerSort(backBanner.getId(), sort);
    }

    @Override
    public void add(CrmBanner banner) {
        // 获取所有的幻灯片数量
        int count = baseMapper.getCount() + 1;
        banner.setSort(count);
        // 新增幻灯片
        baseMapper.insert(banner);
    }
}

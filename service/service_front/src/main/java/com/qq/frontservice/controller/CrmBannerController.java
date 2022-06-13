package com.qq.frontservice.controller;

import com.qq.frontservice.entity.CrmBanner;
import com.qq.frontservice.service.CrmBannerService;
import com.qq.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  幻灯片前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/frontservice/banner")
// @CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    // 获取排在前5位且未删除的幻灯片
    @GetMapping("getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> list = bannerService.getBanners();
        return Result.success().data("banners", list);
    }

}


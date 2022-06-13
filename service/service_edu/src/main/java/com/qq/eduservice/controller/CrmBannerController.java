package com.qq.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.eduservice.entity.CrmBanner;
import com.qq.eduservice.service.CrmBannerService;
import com.qq.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/eduservice/banner")
// @CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 获取Banner分页列表
     *
     * @param current 当前页
     * @param count   每页记录数
     * @return
     */
    @GetMapping("/findPageBanners/{current}/{count}")
    public Result findPageBanners(@PathVariable("current") int current,
                                  @PathVariable("count") int count) {
        //创造page对象
        Page<CrmBanner> teacherPage = new Page<>(current, count);
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        //调用方法实现分页
        //调用方法时，底层已经封装了，把分页所有数据封装到teacherPage对象里面了
        bannerService.page(teacherPage, queryWrapper);

        long total = teacherPage.getTotal(); //总记录数
        List<CrmBanner> banners = teacherPage.getRecords(); //当前页面数据list集合
        return Result.success().data("total", total).data("banners", banners);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/add")
    public Result saveBanner(@RequestBody CrmBanner banner) {
        bannerService.add(banner);
        return Result.success();
    }

    @ApiOperation(value = "往前移Banner")
    @PutMapping("/forward/{sort}/{id}")
    public Result forwardBanner(@PathVariable String id,
                                @PathVariable String sort) {
        bannerService.forwardBanner(id, sort);
        return Result.success();
    }

    @ApiOperation(value = "往后移Banner")
    @PutMapping("/backward/{sort}/{id}")
    public Result backwardBanner(@PathVariable String id,
                                 @PathVariable String sort) {
        bannerService.backwardBanner(id, sort);
        return Result.success();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/delBanner/{id}")
    public Result delBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.success();
    }


}



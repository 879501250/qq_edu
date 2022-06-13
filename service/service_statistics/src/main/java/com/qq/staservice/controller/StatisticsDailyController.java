package com.qq.staservice.controller;


import com.qq.commonutils.Result;
import com.qq.staservice.entity.StatisticsDaily;
import com.qq.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 数据统计前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/staservice")
// @CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 初始化每天的统计数据，并获取
    @GetMapping("/initStatistics/{date}")
    public Result initStatistics(@PathVariable String date) {
        StatisticsDaily statisticsDaily = statisticsDailyService.initStatistics(date);
        return Result.success().data("statistics", statisticsDaily);
    }

    // 为指定日期添加注册人数
    @PutMapping("/addRegisterNum/{date}")
    public Result addRegisterNum(@PathVariable String date) {
        statisticsDailyService.addRegisterNum(date);
        return Result.success();
    }

    // 为指定日期添加登录人数
    @PutMapping("/addLoginNum/{date}")
    public Result addLoginNum(@PathVariable String date) {
        statisticsDailyService.addLoginNum(date);
        return Result.success();
    }

    // 为指定日期添加视频浏览量
    @PutMapping("/addVideoViewNum/{date}")
    public Result addVideoViewNum(@PathVariable String date) {
        statisticsDailyService.addVideoViewNum(date);
        return Result.success();
    }

    // 为指定日期添加文章浏览量
    @PutMapping("/addArticleViewNum/{date}")
    public Result addArticleViewNum(@PathVariable String date) {
        statisticsDailyService.addArticleViewNum(date);
        return Result.success();
    }

    // 获取指定日期的统计记录及相关的比较数据
    @GetMapping("/getStatistics/{date}")
    public Result getStatistics(@PathVariable String date) {
        Map<String, Object> map = statisticsDailyService.getStatistics(date);
        return Result.success().data(map);
    }

    // 随机自动生成指定日期至今的统计数据，若存在则不生成
    @GetMapping("/generateData/{date}")
    public Result generateData(@PathVariable String date){
        statisticsDailyService.generateData(date);
        return Result.success();
    }
}


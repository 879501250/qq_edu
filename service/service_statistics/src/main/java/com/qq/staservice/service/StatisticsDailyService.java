package com.qq.staservice.service;

import com.qq.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    // 初始化每天的统计数据，并获取
    StatisticsDaily initStatistics(String date);

    // 为指定日期添加注册人数
    void addRegisterNum(String date);

    // 为指定日期添加登录人数
    void addLoginNum(String date);

    // 为指定日期添加视频浏览量
    void addVideoViewNum(String date);

    // 为指定日期添加文章浏览量
    void addArticleViewNum(String date);

    // 获取指定日期的统计记录及相关的比较数据
    Map<String, Object> getStatistics(String date);

    // 随机自动生成指定日期至今的统计数据，若存在则不生成
    void generateData(String date);
}

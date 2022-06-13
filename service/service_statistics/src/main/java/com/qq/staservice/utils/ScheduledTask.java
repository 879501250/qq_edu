package com.qq.staservice.utils;

import com.qq.commonutils.DateUtil;
import com.qq.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务类
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 每天凌晨1点执行定时，创建当天的统计任务
     * 使用cron表达式
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取当天的日期
        String day = DateUtil.formatDate(new Date());
        dailyService.initStatistics(day);
    }
}

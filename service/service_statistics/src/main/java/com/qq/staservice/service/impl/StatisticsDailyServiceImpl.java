package com.qq.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.commonutils.DateUtil;
import com.qq.servicebase.exceptionhandler.QqException;
import com.qq.staservice.entity.StatisticsDaily;
import com.qq.staservice.mapper.StatisticsDailyMapper;
import com.qq.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Override
    public StatisticsDaily initStatistics(String date) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", date);
        // 获取指定日期的统计记录
        StatisticsDaily statisticsDaily = baseMapper.selectOne(queryWrapper);
        // 若存在则直接返回
        if (statisticsDaily != null)
            return statisticsDaily;
        else { // 若不存在，则创建一个新的返回
            statisticsDaily = new StatisticsDaily();
            statisticsDaily.setDateCalculated(date);
            // 随机赋值
            statisticsDaily.setArticleViewNum(0);
            statisticsDaily.setRegisterNum(0);
            statisticsDaily.setVideoViewNum(0);
            statisticsDaily.setLoginNum(0);
            this.save(statisticsDaily);
            statisticsDaily = baseMapper.selectOne(queryWrapper);
            return statisticsDaily;
        }
    }

    @Override
    public void addRegisterNum(String date) {
        // 先获取指定日期的统计记录
        StatisticsDaily statisticsDaily = this.initStatistics(date);
        // 修改
        statisticsDaily.setRegisterNum(statisticsDaily.getRegisterNum() + 1);
        this.updateById(statisticsDaily);
    }

    @Override
    public void addLoginNum(String date) {
        // 先获取指定日期的统计记录
        StatisticsDaily statisticsDaily = this.initStatistics(date);
        // 修改
        statisticsDaily.setLoginNum(statisticsDaily.getLoginNum() + 1);
        this.updateById(statisticsDaily);
    }

    @Override
    public void addVideoViewNum(String date) {
        // 先获取指定日期的统计记录
        StatisticsDaily statisticsDaily = this.initStatistics(date);
        // 修改
        statisticsDaily.setVideoViewNum(statisticsDaily.getVideoViewNum() + 1);
        this.updateById(statisticsDaily);
    }

    @Override
    public void addArticleViewNum(String date) {
        // 先获取指定日期的统计记录
        StatisticsDaily statisticsDaily = this.initStatistics(date);
        // 修改
        statisticsDaily.setArticleViewNum(statisticsDaily.getArticleViewNum() + 1);
        this.updateById(statisticsDaily);
    }

    @Override
    public Map<String, Object> getStatistics(String date) {
        Map<String, Object> map = new HashMap<>();
        StatisticsDaily daily;

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> registerList = new ArrayList<>();
        ArrayList<Integer> loginList = new ArrayList<>();
        ArrayList<Integer> videoList = new ArrayList<>();
        ArrayList<Integer> articleList = new ArrayList<>();
        // 获取指定日期七天内的统计数据
        try {
            // 获取近六天内的数据
            for(int i = 6;i>0;i--){
                daily = initStatistics(DateUtil.formatDate(DateUtil.addDays(
                        new SimpleDateFormat("yyyy-MM-dd").parse(date),
                        -i)));
                dateList.add(daily.getDateCalculated()); // 日期
                registerList.add(daily.getRegisterNum()); // 注册人数
                loginList.add(daily.getLoginNum()); // 登录人数
                videoList.add(daily.getVideoViewNum()); // 视频浏览量
                articleList.add(daily.getArticleViewNum()); // 文章浏览量
            }
            daily = initStatistics(date);
            // 封装指定日期的统计数据
            ArrayList<Integer> dataList = new ArrayList<>();
            // 添加数据
            dataList.add(daily.getRegisterNum());
            dataList.add(daily.getLoginNum());
            dataList.add(daily.getVideoViewNum());
            dataList.add(daily.getArticleViewNum());
            // 获取指定日期的统计数据
            map.put("dataList", dataList);
            dateList.add(daily.getDateCalculated()); // 日期
            registerList.add(daily.getRegisterNum()); // 注册人数
            loginList.add(daily.getLoginNum()); // 登录人数
            videoList.add(daily.getVideoViewNum()); // 视频浏览量
            articleList.add(daily.getArticleViewNum()); // 文章浏览量
            // 近七天内的数据
            map.put("dateList", dateList);
            map.put("registerList", registerList);
            map.put("loginList", loginList);
            map.put("videoList", videoList);
            map.put("articleList", articleList);

        } catch (Exception e) {
            throw new QqException(20001, "统计数据失败~");
        }
        return map;
    }

    @Override
    public void generateData(String date) {
        String now = DateUtil.formatDate(new Date());
        while (true){
            if (now.compareTo(date) < 0)
                break;
            try {
                // 加一天
                date = DateUtil.formatDate(DateUtil.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(date), 1));
            } catch (Exception e) {

            }
            // 初始化date的统计数据
            this.initStatistics(date);
        }
    }
}

package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface FirstPageService {

    /**
     * 获取当日任务数
     *
     * @return Result 包含当日任务数量的数据
     */
    Result getTaskCountToday();

    /**
     * 获取当日利用率
     *
     * @return Result 包含当日利用率的数据
     */
    Result getUtilizationRateToday();

    /**
     * 获取实时告警信息
     *
     * @return Result 包含实时告警信息的列表
     */
    Result getRealTimeAlarms();

    /**
     * 添加实时告警信息
     *
     * @param eventId     事件ID
     * @param eventDealer 事件处理人
     * @return Result 表示添加操作的结果
     */
    Result addRealTimeAlarm(String eventId, String eventDealer);

    /**
     * 获取当日事件跟踪信息
     *
     * @return Result 包含当日事件跟踪信息的数据
     */
    Result getTodayEventTracking();

    /**
     * 获取本周事件跟踪信息
     *
     * @return Result 包含本周事件跟踪信息的数据
     */
    Result getThisWeekEventTracking();

    /**
     * 获取本月事件跟踪信息
     *
     * @return Result 包含本月事件跟踪信息的数据
     */
    Result getThisMonthEventTracking();

    /**
     * 获取本年事件跟踪信息
     *
     * @return Result 包含本年事件跟踪信息的数据
     */
    Result getThisYearEventTracking();

}

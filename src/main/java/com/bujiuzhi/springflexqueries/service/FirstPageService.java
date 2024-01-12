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

    // 其他方法定义 ...

    /**
     * 获取当日调用量
     *
     * @return 包含当日调用量数据的 Result 对象
     */
    Result getCallVolumeToday();

    /**
     * 获取数据源表数量
     *
     * @return 包含数据源表数量的 Result 对象
     */
    Result getDataSourceTables();

    /**
     * 获取风险因子数量
     *
     * @return 包含风险因子数量的 Result 对象
     */
    Result getRiskFactors();

    /**
     * 获取业务规则数量
     *
     * @return 包含业务规则数量的 Result 对象
     */
    Result getBusinessRules();

    /**
     * 获取风险类型数量
     *
     * @return 包含风险类型数量的 Result 对象
     */
    Result getRiskTypes();

    /**
     * 获取智能代理数量
     *
     * @return 包含智能代理数量的 Result 对象
     */
    Result getIntelligentAgents();

    /**
     * 获取代理的不同状态及其数量
     *
     * @return 包含代理不同状态及其数量的 Result 对象
     */
    Result getAgentStatusCounts();

    /**
     * 获取模型的不同状态及其数量
     *
     * @return 包含模型不同状态及其数量的 Result 对象
     */
    Result getModelStatusCounts();


}

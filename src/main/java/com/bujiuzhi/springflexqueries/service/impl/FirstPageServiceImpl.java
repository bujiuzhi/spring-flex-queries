package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.FirstPageMapper;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.service.FirstPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FirstPageServiceImpl implements FirstPageService {

    @Autowired
    private FirstPageMapper firstPageMapper;

    @Override
    public Result getTaskCountToday() {
        try {
            Map<String, Object> countResult = firstPageMapper.countTasksToday();
            return Result.success(countResult);
        } catch (Exception e) {
            return Result.error("获取当日任务数失败: " + e.getMessage());
        }
    }

    @Override
    public Result getUtilizationRateToday() {
        try {
            Map<String, Object> utilizationRate = firstPageMapper.calculateUtilizationRateToday();
            return Result.success(utilizationRate);
        } catch (Exception e) {
            return Result.error("获取当日利用率失败: " + e.getMessage());
        }
    }

    @Override
    public Result getRealTimeAlarms() {
        try {
            List<Map<String, Object>> alarms = firstPageMapper.findRealTimeAlarms();
            return Result.success(alarms);
        } catch (Exception e) {
            return Result.error("获取实时告警信息失败: " + e.getMessage());
        }
    }

    @Override
    public Result addRealTimeAlarm(String eventId, String eventDealer) {
        try {
            firstPageMapper.insertRealTimeAlarm(eventId, eventDealer);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加实时告警信息失败: " + e.getMessage());
        }
    }

    @Override
    public Result getTodayEventTracking() {
        try {
            Map<String, Object> todayTracking = firstPageMapper.getTodayEventTracking();
            return Result.success(todayTracking);
        } catch (Exception e) {
            return Result.error("获取当日事件跟踪信息失败: " + e.getMessage());
        }
    }

    @Override
    public Result getThisWeekEventTracking() {
        try {
            Map<String, Object> thisWeekTracking = firstPageMapper.getThisWeekEventTracking();
            return Result.success(thisWeekTracking);
        } catch (Exception e) {
            return Result.error("获取本周事件跟踪信息失败: " + e.getMessage());
        }
    }

    @Override
    public Result getThisMonthEventTracking() {
        try {
            Map<String, Object> thisMonthTracking = firstPageMapper.getThisMonthEventTracking();
            return Result.success(thisMonthTracking);
        } catch (Exception e) {
            return Result.error("获取本月事件跟踪信息失败: " + e.getMessage());
        }
    }

    @Override
    public Result getThisYearEventTracking() {
        try {
            Map<String, Object> thisYearTracking = firstPageMapper.getThisYearEventTracking();
            return Result.success(thisYearTracking);
        } catch (Exception e) {
            return Result.error("获取本年事件跟踪信息失败: " + e.getMessage());
        }
    }

    // 其他方法 ...
    @Override
    public Result getCallVolumeToday() {
        try {
            Map<String, Object> data = firstPageMapper.countCallVolumeToday();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取当日调用量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getDataSourceTables() {
        try {
            Map<String, Object> data = firstPageMapper.countDataSourceTables();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取数据源表数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getRiskFactors() {
        try {
            Map<String, Object> data = firstPageMapper.countRiskFactors();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取风险因子数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getBusinessRules() {
        try {
            Map<String, Object> data = firstPageMapper.countBusinessRules();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取业务规则数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getRiskTypes() {
        try {
            Map<String, Object> data = firstPageMapper.countRiskTypes();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取风险类型数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getIntelligentAgents() {
        try {
            Map<String, Object> data = firstPageMapper.countIntelligentAgents();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取智能代理数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getAgentStatusCounts() {
        try {
            List<Map<String, Object>> data = firstPageMapper.findAgentStatusCounts();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取代理的不同状态及其数量失败: " + e.getMessage());
        }
    }

    @Override
    public Result getModelStatusCounts() {
        try {
            List<Map<String, Object>> data = firstPageMapper.findModelStatusCounts();
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取模型的不同状态及其数量失败: " + e.getMessage());
        }
    }
}
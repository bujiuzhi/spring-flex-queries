package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.service.FirstPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firstPage")
@Validated
public class FirstPageController {

    @Autowired
    private FirstPageService firstPageService;

    /**
     * 获取当日任务数
     *
     * @return Result，包含当日任务数量的数据
     */
    @PostMapping("/taskCountToday")
    public Result getTaskCountToday() {
        return firstPageService.getTaskCountToday();
    }

    /**
     * 获取当日利用率
     *
     * @return Result，包含当日利用率的数据
     */
    @PostMapping("/utilizationRateToday")
    public Result getUtilizationRateToday() {
        return firstPageService.getUtilizationRateToday();
    }

    /**
     * 获取实时告警信息
     *
     * @return Result，包含实时告警信息的列表
     */
    @PostMapping("/realTimeAlarms")
    public Result getRealTimeAlarms() {
        return firstPageService.getRealTimeAlarms();
    }

    /**
     * 添加实时告警信息
     *
     * @param eventId 事件ID
     * @return Result，表示添加操作的结果
     */
    @PostMapping("/addRealTimeAlarm")
    public Result addRealTimeAlarm(@RequestParam String eventId, @RequestParam String eventDealer) {
        return firstPageService.addRealTimeAlarm(eventId, eventDealer);
    }

    /**
     * 获取当日事件跟踪信息
     *
     * @return Result，包含当日事件跟踪信息的数据
     */
    @PostMapping("/todayEventTracking")
    public Result getTodayEventTracking() {
        return firstPageService.getTodayEventTracking();
    }

    /**
     * 获取本周事件跟踪信息
     *
     * @return Result，包含本周事件跟踪信息的数据
     */
    @PostMapping("/thisWeekEventTracking")
    public Result getThisWeekEventTracking() {
        return firstPageService.getThisWeekEventTracking();
    }

    /**
     * 获取本月事件跟踪信息
     *
     * @return Result，包含本月事件跟踪信息的数据
     */
    @PostMapping("/thisMonthEventTracking")
    public Result getThisMonthEventTracking() {
        return firstPageService.getThisMonthEventTracking();
    }

    /**
     * 获取本年事件跟踪信息
     *
     * @return Result，包含本年事件跟踪信息的数据
     */
    @PostMapping("/thisYearEventTracking")
    public Result getThisYearEventTracking() {
        return firstPageService.getThisYearEventTracking();
    }
}

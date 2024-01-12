package com.bujiuzhi.springflexqueries.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FirstPageMapper {

    // 获取当日任务数
    @Select("SELECT COUNT(*) as task_count FROM test.stg_event_info WHERE DATE(event_time) = CURRENT_DATE")
    Map<String, Object> countTasksToday();

    // 获取当日利用率
    @Select("SELECT " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) AS processed_events, " +
            "COUNT(*) AS total_events, " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) / COUNT(*) * 100 AS utilization_rate " +
            "FROM test.stg_event_info WHERE DATE(event_time) = CURRENT_DATE")
    Map<String, Object> calculateUtilizationRateToday();

    // 获取实时告警信息
    @Select("SELECT " +
            "e.event_id, " +
            "e.event_warn_level AS alarm_level, " +
            "e.event_scene AS alarm_category, " +
            "CONCAT('Event Name: ', e.event_name, ', Warning Level: ', e.event_warn_level, ', Event State: ', e.event_state, ', Risk Category: ', e.event_scene) AS detail_info, " +
            "e.event_time AS alarm_time, " +
            "s.event_dealer AS handler " +
            "FROM test.stg_event_info e " +
            "LEFT JOIN test.stg_event_session s ON e.event_id = s.event_id " +
            "WHERE e.event_warn_level = '高' AND e.event_state = '未处理'")
    List<Map<String, Object>> findRealTimeAlarms();

    // 添加实时告警信息
    @Insert("INSERT INTO test.stg_event_session " +
            "(event_session_id, event_session, event_id, event_dealer, event_deal_method, event_auditor, event_audit_comment, event_session_type, process_time, create_time, update_time) " +
            "SELECT " +
            "UUID(), (SELECT MAX(event_session) + 1 FROM test.stg_event_session WHERE event_id = #{eventId}), " +
            "#{eventId}, #{eventDealer}, event_deal_method, event_auditor, event_audit_comment, event_session_type, NOW(), NOW(), NOW() " +
            "FROM test.stg_event_session " +
            "WHERE event_id = #{eventId} ORDER BY create_time DESC LIMIT 1")
    void insertRealTimeAlarm(@Param("eventId") String eventId, @Param("eventDealer") String eventDealer);

    // 获取当日事件跟踪信息
    @Select("SELECT " +
            "SUM(CASE WHEN event_state = '未处理' THEN 1 ELSE 0 END) AS unprocessed_today, " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) AS processed_today " +
            "FROM test.stg_event_info " +
            "WHERE DATE(event_time) = CURRENT_DATE")
    Map<String, Object> getTodayEventTracking();

    // 获取本周事件跟踪信息
    @Select("SELECT " +
            "SUM(CASE WHEN event_state = '未处理' THEN 1 ELSE 0 END) AS unprocessed_this_week, " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) AS processed_this_week " +
            "FROM test.stg_event_info " +
            "WHERE YEARWEEK(event_time, 1) = YEARWEEK(CURRENT_DATE, 1)")
    Map<String, Object> getThisWeekEventTracking();

    // 获取本月事件跟踪信息
    @Select("SELECT " +
            "SUM(CASE WHEN event_state = '未处理' THEN 1 ELSE 0 END) AS unprocessed_this_month, " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) AS processed_this_month " +
            "FROM test.stg_event_info " +
            "WHERE MONTH(event_time) = MONTH(CURRENT_DATE) AND YEAR(event_time) = YEAR(CURRENT_DATE)")
    Map<String, Object> getThisMonthEventTracking();

    // 获取本年事件跟踪信息
    @Select("SELECT " +
            "SUM(CASE WHEN event_state = '未处理' THEN 1 ELSE 0 END) AS unprocessed_this_year, " +
            "SUM(CASE WHEN event_state = '处理完成' THEN 1 ELSE 0 END) AS processed_this_year " +
            "FROM test.stg_event_info " +
            "WHERE YEAR(event_time) = YEAR(CURRENT_DATE)")
    Map<String, Object> getThisYearEventTracking();

    // 其他 SQL 查询和方法定义 ...

    // 获取当日调用量
    @Select("SELECT COUNT(*) AS count FROM test.pt_comm_server_tran WHERE tran_ID LIKE CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '%')")
    Map<String, Object> countCallVolumeToday();

    // 获取数据源表数量
    @Select("SELECT COUNT(*) AS count FROM test.risk_center_table WHERE is_rule_dataset = 0")
    Map<String, Object> countDataSourceTables();

    // 获取风险因子数量
    @Select("SELECT COUNT(*) AS count FROM test.risk_center_table WHERE is_rule_dataset = 1")
    Map<String, Object> countRiskFactors();

    // 获取业务规则数量
    @Select("SELECT COUNT(*) AS count FROM test.stg_rule_info")
    Map<String, Object> countBusinessRules();

    // 获取风险类型数量
    @Select("SELECT COUNT(*) AS count FROM test.stg_model_job")
    Map<String, Object> countRiskTypes();

    // 获取智能代理数量
    @Select("SELECT COUNT(*) AS count FROM test.stg_agency_table")
    Map<String, Object> countIntelligentAgents();

    // 获取代理的不同状态及其数量
    @Select("SELECT agency_status, COUNT(1) AS count FROM test.stg_agency_table GROUP BY agency_status")
    List<Map<String, Object>> findAgentStatusCounts();

    // 获取模型的不同状态及其数量
    @Select("SELECT job_status, COUNT(1) AS count FROM test.stg_model_job GROUP BY job_status")
    List<Map<String, Object>> findModelStatusCounts();

}
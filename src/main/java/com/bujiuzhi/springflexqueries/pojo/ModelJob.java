package com.bujiuzhi.springflexqueries.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * ModelJob类表示数据库中的一个模型作业记录。
 * 它包含了模型作业的所有相关信息，如模型ID、名称、训练数据比例等。
 */
@Data
public class ModelJob {

    @NotBlank(message = "模型ID不能为空")
    private String model_id; // 模型的唯一标识符

    private String model_name; // 模型的名称
    private String label; // 模型的标签或分类
    private String task_description; // 模型任务的描述
    private String success_criteria; // 成功评价标准
    private String success_value; // 成功评价的值
    private String data_set; // 使用的数据集名称
    private String start_date; // 训练开始日期
    private String end_date; // 训练结束日期
    private String train_data_percentage; // 训练数据所占的百分比
    private String test_data_percentage; // 测试数据所占的百分比
    private String sampling_method; // 采样方法
    private String algorithm_type; // 使用的算法类型
    private String rate; // 学习率
    private String loss; // 损失函数类型
    private String batch_size; // 批次大小
    private String epochs; // 训练周期数
    private String performanceIndicators; // 性能指标
    private String updateStrategy; // 更新策略
    private String updateFrequency; // 更新频率
    private String monitoringFrequency; // 监控频率
    private String monitoringTime; // 监控时间
    private String optimizationGoals; // 优化目标
    private String effective_time; // 生效时间
    private String specific_effective_time; // 特定的生效时间
    private String model_status; // 模型的状态

    @NotBlank(message = "模型版本号不能为空")
    private String model_version; // 模型的版本

    private String create_user; // 创建模型作业的用户
    private String create_time; // 创建模型作业的时间
}
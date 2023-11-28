package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 模型作业实体类，对应数据库中的stg_model_job表。
 */
@Data
public class StgModelJob {
    @NotBlank(message = "作业ID不能为空", groups = Update.class)
    private String jobId; // 模型作业ID，在更新操作时不能为空

    @NotBlank(message = "模型ID不能为空", groups = Insert.class)
    private String modelId; // 模型ID，在插入操作时不能为空

    @NotBlank(message = "模型版本不能为空", groups = Insert.class)
    private String modelVersion; // 模型版本

    private String modelName; // 模型的名称

    private String modelCategory; // 模型分类，例如信用模型、归因模型、估值模型、预警模型

    private String algorithmName; // 模型算法，例如k-means, clarans, cure, dbscan

    private String algorithmParameters; // 算法的参数，通常以JSON格式存储

    private String sampleDivision; // 样本划分，例如训练数据、验证数据、测试数据

    private String trainingProgress; // 训练进度，以百分比表示

    private String modelStatus; // 模型状态，例如训练中、训练完成

    private String triggerMechanism; // 触发机制（更新策略），例如指标预警、手工触发、定期触发

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTrainingTime; // 最近一次训练的时间

    private String lastTrainingDuration; // 最近一次训练的耗时，例如1小时

    private String taskDescription; // 任务描述

    private String successCriteria; // 成功准则，以JSON格式存储

    private String dataSet; // 数据集名称

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSetStartDate; // 数据集起始日期

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSetEndDate; // 数据集结束日期

    private Float trainingDataPercentage; // 训练数据所占的百分比

    private Float testDataPercentage; // 测试数据所占的百分比

    private String samplingMethod; // 抽样方法，例如随机、分层、系统

    private String performanceMetrics; // 性能指标，以JSON格式存储

    private String updateFrequency; // 更新频率，例如每天、每周、每月、每季度、每年

    private String monitoringFrequency; // 监控频率，例如每天、每周、每月、每季度、每年

    private String optimizationGoal; // 优化目标，例如最大化AUC值、最小化误差

    private String effectiveTime; // 生效时间，例如一直生效或指定时间区间

    private String monitoringScenario; // 监控场景标签，例如信用风险

    private String modelPmml; // 模型PMML

    private String sampleData; // 样本数据列

    private String creator; // 创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime; // 创建时间

    private String updater; // 更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间


    //public interface Insert extends Default {}
    public interface Insert {
    }

    //如果说某个校验项没有指定分组,默认属于Default分组
    //分组之间可以继承, A extends B  那么A中拥有B中所有的校验项

    //public interface Update extends Default {}
    public interface Update {
    }

}

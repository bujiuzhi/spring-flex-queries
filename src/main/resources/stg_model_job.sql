-- 删除表
DROP TABLE IF EXISTS test.stg_model_job;

-- 创建表
CREATE TABLE test.stg_model_job
(
    job_id                   VARCHAR(255) NOT NULL COMMENT '任务ID，唯一标识每条记录',
    model_id       VARCHAR(255) NOT NULL COMMENT '模型的ID',
    model_version            VARCHAR(255) NOT NULL COMMENT '模型版本',
    model_name               VARCHAR(255) COMMENT '模型的名称',
    model_category           VARCHAR(255) COMMENT '模型分类，例如信用模型、归因模型、估值模型、预警模型',
    algorithm_name VARCHAR(255) COMMENT '模型算法，例如K-MEANS, CLARANS, CURE, DBSCAN',
    algorithm_parameters     TEXT COMMENT '算法的参数，通常以JSON格式存储',
    sample_division          VARCHAR(255) COMMENT '样本划分，例如训练数据、验证数据、测试数据',
    training_progress        FLOAT COMMENT '训练进度，以百分比表示',
    model_status   VARCHAR(255) COMMENT '模型状态，例如运行中、运行失败、运行成功',
    job_status     VARCHAR(255) COMMENT '任务状态，例如测试，运行，中断，下线',
    trigger_mechanism        VARCHAR(255) COMMENT '触发机制（更新策略），例如指标预警、手工触发、定期触发',
    -- last_training_time       DATETIME COMMENT '最近一次训练的时间',
    last_training_time       VARCHAR(255) COMMENT '最近一次训练的时间',
    last_training_duration   VARCHAR(255) COMMENT '最近一次训练的耗时，例如1小时',
    task_description         TEXT COMMENT '任务描述',
    success_criteria         TEXT COMMENT '成功准则，以JSON格式存储',
    data_set                 VARCHAR(255) COMMENT '数据集名称',
    -- data_set_start_date      DATE COMMENT '数据集起始日期',
    data_set_start_date      VARCHAR(255) COMMENT '数据集起始日期',
    -- data_set_end_date        DATE COMMENT '数据集结束日期',
    data_set_end_date        VARCHAR(255) COMMENT '数据集结束日期',
    training_data_percentage FLOAT COMMENT '训练数据所占的百分比',
    test_data_percentage     FLOAT COMMENT '测试数据所占的百分比',
    sampling_method          VARCHAR(255) COMMENT '抽样方法，例如随机、分层、系统',
    performance_metrics      TEXT COMMENT '性能指标，以JSON格式存储',
    update_frequency         VARCHAR(255) COMMENT '更新频率，例如每天、每周、每月、每季度、每年',
    monitoring_frequency     VARCHAR(255) COMMENT '监控频率，例如每天、每周、每月、每季度、每年',
    optimization_goal        VARCHAR(255) COMMENT '优化目标，例如最大化AUC值、最小化误差',
    effective_time           TEXT COMMENT '生效时间，例如一直生效或指定时间区间',
    monitoring_scenario      VARCHAR(255) COMMENT '监控场景标签，例如信用风险',
    model_pmml               TEXT COMMENT '模型PMML',
    sample_data              TEXT COMMENT '样本数据列',
    creator                  VARCHAR(255) COMMENT '创建者',
    -- creation_time            DATETIME COMMENT '创建时间',
    creation_time            VARCHAR(255) COMMENT '创建时间',
    updater                  VARCHAR(255) COMMENT '更新者',
    -- update_time              DATETIME COMMENT '更新时间',
    update_time              VARCHAR(255) COMMENT '更新时间',

    PRIMARY KEY (job_id)
) COMMENT ='存储模型作业信息的表';

-- 插入数据
INSERT INTO test.stg_model_job
(job_id, model_id, model_version, model_name, model_category, algorithm_name,
 algorithm_parameters, sample_division, training_progress, model_status, job_status,
 trigger_mechanism, last_training_time, last_training_duration, task_description,
 success_criteria, data_set, data_set_start_date, data_set_end_date,
 training_data_percentage, test_data_percentage, sampling_method,
 performance_metrics, update_frequency, monitoring_frequency,
 optimization_goal, effective_time, monitoring_scenario, model_pmml, sample_data,
 creator, creation_time, updater, update_time)
VALUES ('20230101010000-MFSFMK202309270001-1dot0', 'MFSFMK202309270001', '1.0', '信用评分模型', '信用模型', 'K-MEANS',
        '{"param1": "value1"}', '训练数据', 50.0, '运行中', '测试',
        '指标预警', '2023-01-10 13:00:00', '', '信用评分模型训练', '{"accuracy": 0.92}', 'credit_score_training',
        '2022-01-01', '2022-06-01', 70.0, 30.0, '随机', '{"AUC": 0.90}', '每天', '每天',
        '最大化AUC', '{"always": true}', '信用风险', '<PMML_data>', '<Sample_data>',
        '张三', '2023-01-01 01:00:00', '李四', '2023-01-10 14:00:00'),
       ('20230101080000-MFSFMK202309270001-2dot0', 'MFSFMK202309270001', '2.0', '信用评分模型', '信用模型', 'K-MEANS',
        '{"param1": "value1","param2": "value2"}', '训练数据', 0.0, '运行失败', '运行',
        '指标预警', '2023-01-10 13:00:00', '', '信用评分模型训练', '{"accuracy": 0.92}', 'credit_score_training',
        '2022-01-01', '2022-06-01', 70.0, 30.0, '随机', '{"AUC": 0.90}', '每天', '每天',
        '最大化AUC值', '{"always": true}', '信用风险', '<PMML_data>', '<Sample_data>',
        '张三', '2023-01-01 08:00:00', '李四', '2023-01-10 14:00:00'),
       ('20230201093000-MFSFMK202309270001-2dot0', 'MFSFMK202309270001', '2.0', '客户流失预测', '预警模型', 'CLARANS',
        '{"param1": "value1"}', '测试数据', 50.0, '运行中', '下线',
        '手工触发', '2023-02-15 15:30:00', '', '客户流失率分析与预测', '{"f1_score": 0.88}', 'churn_analysis',
        '2022-02-01', '2022-07-01', 60.0, 40.0, '分层', '{"precision": 0.80}', '每周', '每周',
        '最小化误分类率', '{"start": "2023-02-01", "end": "2023-02-15"}', '客户关系管理', '<PMML_data>',
        '<Sample_data>',
        '王五', '2023-02-01 09:30:00', '赵六', '2023-02-15 16:30:00'),
       ('20230301100000-MFSFMK202309270001-1dot0', 'MFSFMK202309270001', '1.0', '商品推荐模型', '归因模型', 'CURE',
        '{"param1": "value1"}', '验证数据', 0.0, '没有状态', '中断',
        '定期触发', '2023-03-20 16:00:00', '', '个性化商品推荐模型训练', '{"recall": 0.93}', 'product_recommendation',
        '2022-03-01', '2022-08-01', 50.0, 50.0, '系统', '{"NDCG": 0.85}', '每月', '每月',
        '最大化召回率', '{"start": "2023-03-01", "end": "2023-03-20"}', '销售优化', '<PMML_data>', '<Sample_data>',
        '孙七', '2023-03-01 10:00:00', '周八', '2023-03-20 17:00:00'),
       ('20230401120000-MFSFMK202309270001-3dot0', 'MFSFMK202309270001', '3.0', '故障预测模型', '预警模型', 'CURE',
        '{"param1": "value1"}', '训练数据', 100.0, '运行成功', '测试',
        '指标预警', '2023-04-25 17:00:00', '', '故障率预测', '{"precision": 0.87}', 'fault_prediction',
        '2022-04-01', '2022-09-01', 80.0, 20.0, '随机', '{"MSE": 0.03}', '每季度', '每季度',
        '最小化均方误差', '{"always": true}', '设备维护', '<PMML_data>', '<Sample_data>',
        '刘九', '2023-04-01 12:00:00', '吴十', '2023-04-25 18:00:00'),
       ('20230501130000-MFSFMK202309270001-2dot0', 'MFSFMK202309270001', '2.0', '市场细分模型', '归因模型', 'DBSCAN',
        '{"param1": "value1"}', '测试数据', 50.0, '运行中', '测试', '手工触发', '2023-05-30 18:00:00', '',
        '市场细分与消费者行为分析', '{"accuracy": 0.89}', 'market_segmentation',
        '2022-05-01', '2022-10-01', 55.0, 45.0, '系统', '{"F1": 0.91}', '每月', '每月',
        '最大化准确率', '{"start": "2023-05-01", "end": "2023-05-30"}', '市场分析', '<PMML_data>', '<Sample_data>',
        '陈十一', '2023-05-01 13:00:00', '林十二', '2023-05-30 19:00:00');


-- 查询数据
SELECT *
FROM test.stg_model_job;



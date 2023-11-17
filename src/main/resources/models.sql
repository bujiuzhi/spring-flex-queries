-- Dropping the table if it already exists
DROP TABLE IF EXISTS test.model_jobs;

-- Creating the table with the specified columns and comments
CREATE TABLE test.model_jobs
(
    model_id                VARCHAR(50) PRIMARY KEY COMMENT '模型的唯一标识符',
    model_name              VARCHAR(50) COMMENT '模型的名称',
    label                   VARCHAR(50) COMMENT '与模型相关联的监控场景标签',
    task_description        VARCHAR(255) COMMENT '模型任务的详细描述',
    success_criteria        VARCHAR(50) COMMENT '模型成功的标准（如命中率、准确率等）',
    success_value           VARCHAR(50) COMMENT '成功标准的具体值',
    data_set                VARCHAR(50) COMMENT '使用的数据集名称',
    start_date              VARCHAR(50) COMMENT '数据集的起始日期',
    end_date                VARCHAR(50) COMMENT '数据集的结束日期',
    train_data_percentage   VARCHAR(50) COMMENT '用于训练的数据集的百分比',
    test_data_percentage    VARCHAR(50) COMMENT '用于测试的数据集的百分比',
    sampling_method         VARCHAR(50) COMMENT '使用的抽样方法',
    algorithm_type          VARCHAR(50) COMMENT '使用的算法类型',
    rate                    VARCHAR(50) COMMENT '学习率',
    loss                    VARCHAR(50) COMMENT '损失函数',
    batch_size              VARCHAR(50) COMMENT '批处理大小',
    epochs                  VARCHAR(50) COMMENT '迭代次数',
    performanceIndicators   VARCHAR(50) COMMENT '性能指标',
    updateStrategy          VARCHAR(50) COMMENT '更新策略',
    updateFrequency         VARCHAR(50) COMMENT '更新频率',
    monitoringFrequency     VARCHAR(50) COMMENT '监控频率',
    monitoringTime          VARCHAR(50) COMMENT '监控时间',
    optimizationGoals       VARCHAR(50) COMMENT '优化目标',
    effective_time          VARCHAR(50) COMMENT '模型生效时间',
    specific_effective_time VARCHAR(50) COMMENT '模型指定生效的具体时间',
    model_status            VARCHAR(50) COMMENT '模型当前的状态',
    model_version           VARCHAR(50) COMMENT '模型的版本号',
    create_user             VARCHAR(50) COMMENT '模型创建者的用户名',
    create_time             VARCHAR(50) COMMENT '模型创建的日期'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='存储模型作业信息的表';


-- Inserting values into the table
INSERT INTO test.model_jobs (model_id, model_name, label, task_description, success_criteria, success_value,
                             data_set, start_date, end_date, train_data_percentage, test_data_percentage,
                             sampling_method,
                             algorithm_type, rate, loss, batch_size, epochs, performanceIndicators, updateStrategy,
                             updateFrequency, monitoringFrequency, monitoringTime, optimizationGoals, effective_time,
                             specific_effective_time, model_status, model_version, create_user,
                             create_time)
VALUES ('model_2001', '风险评估模型', '信用模型', '评估个人信用风险', 'AUC值',
        '0.85', '个人信用数据集', '2023-01-01', '2023-01-31', 70, 30, '随机',
        '逻辑回归', 0.01, '交叉熵', 32, 100, NULL, '定期触发', '每月一次', '每周',
        '09:00-18:00', '最大化AUC值', '一直生效', NULL, '运行中', 'v1.0', '王小明',
        '2023-01-02'),
       ('model_2002', '市场趋势分析', '市场模型', '分析市场趋势和变化', '准确率',
        '0.90', '市场数据集', '2023-02-01', '2023-02-28', 80, 20, '系统',
        '随机森林', 0.001, '均方误差', 64, 50, '准确率, 召回率', '手工触发',
        '按需', '每天', '10:00-16:00', '最小化误差', '指定时间',
        '2023-02-15 12:00:00', '测试', 'v1.1', '李四', '2023-02-03'),
       ('model_2003', '操作风险监控', '风险管理', '监控和管理操作风险', '命中率',
        '0.75', '风险监控数据集', '2023-03-01', '2023-03-30', 60, 40, '分层',
        '支持向量机', 0.005, '对数损失', 128, 200, '命中率, 精确率', '指标预警',
        '每季度', '每月', '15:00-17:00', '风险最小化', '一直生效', NULL, '中止',
        'v1.2', '赵五', '2023-03-05'),
       ('model_2004', '客户满意度调研', '营销分析', '调研客户满意度', '满意度指数',
        '0.80', '客户调研数据集', '2023-04-01', NULL, 50, 50, NULL, '因子分析',
        0.02, NULL, 16, 30, '满意度指数, 准确率', NULL, '需求时', '每月',
        '11:00-14:00', '最大化满意度指数', '指定时间', '2023-04-15 09:00:00',
        '活跃', 'v2.0', '周七', '2023-04-02'),
       ('model_2005', '产品推荐引擎', '推荐系统', '根据用户行为推荐产品', '点击率',
        '0.70', '用户行为数据集', NULL, NULL, 60, 40, '随机', '协同过滤', 0.03,
        '均方对数误差', 64, 100, '点击率, 转化率', '定时触发', '每两周', '每周',
        '工作日10:00-16:00', '转化率最大化', '指定时间', '2023-05-01 00:00:00',
        '待部署', 'v2.2', '孙八', '2023-04-15'),
       ('model_2006', '供应链优化', '物流管理', '优化供应链流程', '成本节约率',
        '0.50', '供应链数据集', '2023-03-15', NULL, NULL, NULL, '分层', '线性规划',
        '0.01', '绝对误差', '32', '40', '成本节约率, 运营效率', '按需触发',
        '需要时', '每季度', '09:00-17:00', '成本最小化', '一直生效',
        NULL, '运行中', 'v3.0', '吴九', '2023-03-20'),
       ('model_2007', '销售预测模型', '销售', '基于历史数据预测销售趋势', '误差率',
        '0.10', '销售数据集', '2023-05-01', '2023-05-31', '85', NULL, '随机',
        '线性回归', '0.02', '均方误差', '24', '150', '误差率, 准确率', '自动触发',
        '每周一次', '每日', '18:00-20:00', '误差最小化', '一直生效', NULL, '激活',
        'v1.1', '陈十', '2023-05-02'),
       ('model_2008', '网络流量分析', '网络安全', '分析网络流量以识别异常',
        '检测率', '0.95', '网络日志数据集', '2023-06-01', '2023-06-30', '90', '10',
        '系统', '深度学习', '0.001', '交叉熵', '48', '60', '检测率, 响应速度',
        '实时触发', '实时', '全天', '10:00-22:00', '响应速度最大化', '一直生效',
        NULL, '运行中', 'v1.5', '钱十一', '2023-06-01'),
       ('model_2009', '投资风险评估', '财务模型', '评估投资项目风险', '回报率',
        '0.80', '投资风险数据集', '2023-07-01', '2023-07-31', NULL, '50', '系统',
        '蒙特卡洛模拟', '0.01', '均方误差', '20', '80', '回报率, 准确率',
        '定期触发', '每季度', '每周', '13:00-14:00', '风险最小化', '指定时间',
        '2023-07-15 12:00:00', '评估中', 'v2.0', '孔明', '2023-07-01'),
       ('model_2010', '客户细分模型', '市场营销', '根据客户数据进行市场细分',
        '覆盖率', '0.77', '市场分析数据集', '2023-07-01', '2023-07-31', NULL, '50',
        '系统', 'K-均值聚类', '0.02', '平方误差', '32', '80', '覆盖率, 精确率',
        '实时触发', '每月', '每周', '周一至周五 09:00-11:00', '市场覆盖最大化', '一直生效',
        NULL, '待验证', 'v1.3', '林俊杰', '2023-07-02');


-- Selecting all records to display
SELECT *
FROM test.model_jobs;


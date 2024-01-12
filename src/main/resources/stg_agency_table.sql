DROP TABLE IF EXISTS test.stg_agency_table;

CREATE TABLE test.stg_agency_table
(
    `agency_id`            varchar(255) NOT NULL COMMENT '代理id',
    `agency_name`          varchar(255) COMMENT '代理名称',
    `monitoring_scene`     varchar(255) COMMENT '监控场景',
    `disposition_strategy` varchar(255) COMMENT '处置策略',
    `execute_strategy`     varchar(255) COMMENT '执行策略',
    `evaluate_index`       varchar(255) COMMENT '评价指标',
    `create_name`          varchar(255) COMMENT '创建人',
    `strategy_arrange`     varchar(255) COMMENT '策略编排',
    `succeed_criterion`    varchar(255) COMMENT '成功准则',
    `agency_status`        varchar(255) COMMENT '代理状态',
    `agency_type`          varchar(255) COMMENT '代理分类',
    `task_describe`        varchar(3000) COMMENT '任务描述',
    `agency_version`       varchar(255) COMMENT '代理版本',
    `agency_level`         varchar(255) COMMENT '代理级别',
    `agency_score` double COMMENT '分数',
    `intervention_type`    varchar(20) COMMENT '干预类型',
    `effective_time`       timestamp COMMENT '生效时间',
    `create_time`          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updator`              varchar(50) COMMENT '更新人',
    `update_time`          timestamp COMMENT '更新时间'
);

INSERT INTO test.stg_agency_table (agency_id, create_time, agency_name, monitoring_scene, disposition_strategy,
                                   execute_strategy, evaluate_index, create_name, strategy_arrange, succeed_criterion,
                                   agency_status, agency_type, task_describe, agency_version, agency_level,
                                   agency_score, intervention_type, effective_time, updator, update_time)
VALUES ('A001', CURRENT_TIMESTAMP, '代理1', '场景1', '策略1', '执行1', '指标1', '创建者1', '编排1', '准则1', '状态1',
        '分类1', '描述1', '版本1', '级别1', 1.0, '类型1', CURRENT_TIMESTAMP, '更新者1', CURRENT_TIMESTAMP),
       ('A002', CURRENT_TIMESTAMP, '代理2', '场景2', '策略2', '执行2', '指标2', '创建者2', '编排2', '准则2', '状态2',
        '分类2', '描述2', '版本2', '级别2', 2.0, '类型2', CURRENT_TIMESTAMP, '更新者2', CURRENT_TIMESTAMP),
       ('A003', CURRENT_TIMESTAMP, '代理3', '场景3', '策略3', '执行3', '指标3', '创建者3', '编排3', '准则3', '状态3',
        '分类3', '描述3', '版本3', '级别3', 3.0, '类型3', CURRENT_TIMESTAMP, '更新者3', CURRENT_TIMESTAMP);

SELECT *
FROM test.stg_agency_table;


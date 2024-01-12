DROP TABLE IF EXISTS test.stg_rule_info;

CREATE TABLE test.stg_rule_info
(
    `rule_id`          varchar(50) NOT NULL COMMENT '规则id',
    `rule_name`        varchar(200) COMMENT '规则名称',
    `rule_type`        varchar(20) COMMENT '规则类型',
    `monitor_scene`    varchar(20) COMMENT '监控场景',
    `rule_state`       varchar(20) COMMENT '规则状态',
    `task_describe`    text COMMENT '任务描述',
    `success_criteria` text COMMENT '成功准则',
    `version`          varchar(50) COMMENT '规则版本号',
    `creator`          varchar(50) COMMENT '创建人',
    `DRL`              text COMMENT 'DRL',
    `drl_column`       varchar(1000) COMMENT 'DRL列',
    `effective_time`   timestamp COMMENT '生效时间',
    `create_time`      timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updator`          varchar(50) COMMENT '更新人',
    `update_time`      timestamp COMMENT '更新时间',
    `type`             varchar(200) COMMENT '数据类型',
    `front_ele`        varchar(1000) COMMENT '前端元素',
    `rule_result_exp`  varchar(500) COMMENT '规则结果表达式',
    `table_name`       varchar(200) COMMENT '表名'
);

INSERT INTO test.stg_rule_info (rule_id, create_time, rule_name, rule_type, monitor_scene, rule_state, task_describe,
                                success_criteria, version, creator, DRL, drl_column, effective_time, updator,
                                update_time, type, front_ele, rule_result_exp, table_name)
VALUES ('R001', CURRENT_TIMESTAMP, '规则1', '类型1', '场景1', '状态1', '描述1', '准则1', 'V1.0', '创建者1', 'DRL1',
        'DRL列1', CURRENT_TIMESTAMP, '更新者1', NULL, '数据类型1', '前端元素1', '规则结果表达式1', '表名1'),
       ('R002', CURRENT_TIMESTAMP, '规则2', '类型2', '场景2', '状态2', '描述2', '准则2', 'V2.0', '创建者2', 'DRL2',
        'DRL列2', CURRENT_TIMESTAMP, '更新者2', NULL, '数据类型2', '前端元素2', '规则结果表达式2', '表名2'),
       ('R003', CURRENT_TIMESTAMP, '规则3', '类型3', '场景3', '状态3', '描述3', '准则3', 'V3.0', '创建者3', 'DRL3',
        'DRL列3', CURRENT_TIMESTAMP, '更新者3', NULL, '数据类型3', '前端元素3', '规则结果表达式3', '表名3');

SELECT *
FROM test.stg_rule_info;

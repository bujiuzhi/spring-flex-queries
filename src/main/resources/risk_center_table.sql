DROP TABLE IF EXISTS test.risk_center_table;

CREATE TABLE test.risk_center_table
(
    id                   BIGINT(20) UNSIGNED NOT NULL COMMENT 'id',
    resource_table       VARCHAR(64)  NOT NULL COMMENT '表名称',
    resource_table_name  VARCHAR(64)  NOT NULL COMMENT '表中文名',
    resource_system_name VARCHAR(64)  NOT NULL COMMENT '系统名称',
    transport_method     TINYINT(4) NOT NULL COMMENT '传输方式：1 批量 2 实时 3 手工',
    transport_channel    TINYINT(4) NOT NULL COMMENT '同步类型：1 数据湖 2 事件平台 3 手工导入',
    is_realtime          TINYINT(4) NOT NULL COMMENT '数据时效：0 是 1 否实时',
    scene_flag           VARCHAR(128) NOT NULL COMMENT '场景标签',
    is_test_dataset      TINYINT(4) NOT NULL COMMENT '0 是 1 否测试集',
    insert_time          DATETIME     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '插入时间',
    create_time          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_rule_dataset      VARCHAR(128) NOT NULL COMMENT '数据角色：0-规则集，1-测试集'
);

INSERT INTO test.risk_center_table (id, resource_table, resource_table_name, resource_system_name, transport_method,
                                    transport_channel, is_realtime, scene_flag, is_test_dataset, insert_time,
                                    create_time, update_time, is_rule_dataset)
VALUES (1, 'table1', '表1', '系统1', 1, 1, 0, '标签1', 0, '2022-01-01 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '0'),
       (2, 'table2', '表2', '系统2', 2, 2, 1, '标签2', 1, '2022-01-02 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '1'),
       (3, 'table3', '表3', '系统3', 3, 3, 0, '标签3', 1, '2022-01-03 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '1');

SELECT *
FROM test.risk_center_table;

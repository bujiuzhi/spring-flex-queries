drop table if exists test.stg_event_info_detail;
CREATE TABLE test.stg_event_info_detail
(
    `event_detail_id` varchar(100) COLLATE utf8mb4_bin  NOT NULL COMMENT '详情ID，唯一标识每条记录',
    `event_id`        varchar(100) COLLATE utf8mb4_bin  NOT NULL COMMENT '事件ID，关联事件表',
    `event_type`      varchar(10) COLLATE utf8mb4_bin   NOT NULL COMMENT '事件类别，1:规则, 2:模型',
    `agency_id`       varchar(100) COLLATE utf8mb4_bin  NOT NULL COMMENT '代理ID',
    `rule_id`         varchar(100) COLLATE utf8mb4_bin  NOT NULL COMMENT '规则ID',
    `model_id`        varchar(100) COLLATE utf8mb4_bin  NOT NULL COMMENT '模型ID',
    `event_metadata`  varchar(2000) COLLATE utf8mb4_bin NOT NULL COMMENT '事件元数据信息',
    `create_time`     timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`event_detail_id`)
) COMMENT = '存储事件触发事件元信息以及规则元信息';
INSERT INTO test.stg_event_info_detail (event_detail_id, event_id, event_type, agency_id, rule_id, model_id,
                                        event_metadata, create_time, update_time)
VALUES ('ED1001', 'EVT1001', '规则', 'AG1001', 'R1001', 'M1001', 'Metadata1', NOW(), NOW()),
       ('ED1002', 'EVT1002', '模型', 'AG1002', 'R1002', 'M1002', 'Metadata2', NOW(), NOW()),
       ('ED1003', 'EVT1003', '规则', 'AG1003', 'R1003', 'M1003', 'Metadata3', NOW(), NOW());
select *
from test.stg_event_info_detail;

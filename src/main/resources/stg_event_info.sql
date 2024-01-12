drop table if exists test.stg_event_info;
CREATE TABLE test.stg_event_info
(
    `event_id`          varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '事件ID，唯一标识每条记录',
    `event_name`        varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '事件名称',
    `event_warn_level`  varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '预警等级',
    `event_time`        timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '事件识别时间',
    `event_scene`       varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '风险类别',
    `event_state`       varchar(20) COLLATE utf8mb4_bin  NOT NULL COMMENT '事件状态',
    `event_dealer`      varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '处理人',
    `event_deal_method` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '处理措施',
    `event_session`     varchar(20) COLLATE utf8mb4_bin  NOT NULL COMMENT '会话id',
    `create_time`       timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`event_id`)
) COMMENT '存储模型作业信息的表';
INSERT INTO test.stg_event_info (event_id, event_name, event_warn_level, event_time, event_scene, event_state,
                                 event_dealer, event_deal_method, event_session)
VALUES ('1', 'Test Event 1', '高', NOW(), '风险类别1', '处理中', '张三', '处理措施A', 'Session1'),
       ('2', 'Test Event 2', '中', NOW(), '风险类别2', '处理完成', '李四', '处理措施B', 'Session2'),
       ('3', 'Test Event 3', '低', NOW(), '风险类别3', '未处理', '王五', '处理措施C', 'Session3'),
       ('4', 'Test Event 4', '高', NOW(), '风险类别1', '处理中', '赵六', '处理措施D', 'Session4'),
       ('5', 'Test Event 5', '中', NOW(), '风险类别2', '处理完成', '周七', '处理措施E', 'Session5');
INSERT INTO test.stg_event_info (event_id, event_name, event_warn_level, event_time, event_scene, event_state,
                                 event_dealer, event_deal_method, event_session)
VALUES ('EVT1001', 'Network Intrusion', '高', NOW(), '网络安全', '未处理', '张三', '检查系统', 'S1001'),
       ('EVT1002', 'Unauthorized Access', '中', NOW(), '系统访问', '处理中', '李四', '更新权限', 'S1002'),
       ('EVT1003', 'Data Leakage', '低', NOW(), '数据泄露', '已处理', '王五', '加强加密', 'S1003');

select *
from test.stg_event_info;
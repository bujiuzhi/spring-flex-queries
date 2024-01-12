drop table if exists test.stg_event_session;
CREATE TABLE test.stg_event_session
(
    `event_session_id`    varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '会话ID，唯一标识每条记录',
    `event_session`       varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '会话数，增量+1',
    `event_id`            varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '事件ID',
    `event_dealer`        varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '处理人，由代理指派',
    `event_deal_method`   varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '处理措施',
    `event_auditor`       varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '审核人，处理人指派',
    `event_audit_comment` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '审核结论',
    `event_session_type`  varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '会话类型，1:处理中, 2:审核中, 0:关闭',
    `process_time`        timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '处理时间',
    `create_time`         timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         timestamp                        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`event_session_id`)
) COMMENT = '存储事件复核信息的表';
INSERT INTO test.stg_event_session (event_session_id, event_session, event_id, event_dealer, event_deal_method,
                                    event_auditor, event_audit_comment, event_session_type, process_time, create_time,
                                    update_time)
VALUES ('ES1001', 1, 'EVT1001', '张三', '检查系统', '审计员A', '合规', '处理中', NOW(), NOW(), NOW()),
       ('ES1002', 2, 'EVT1002', '李四', '更新权限', '审计员B', '需要进一步行动', '审核中', NOW(), NOW(), NOW()),
       ('ES1003', 3, 'EVT1003', '王五', '加强加密', '审计员C', '已解决', '关闭', NOW(), NOW(), NOW());

select *
from test.stg_event_session;


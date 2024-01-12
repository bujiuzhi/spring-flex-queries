-- 删除已存在的表
DROP TABLE IF EXISTS test.stg_message;

-- 创建新表
CREATE TABLE test.stg_message
(
    id          VARCHAR(255) NOT NULL COMMENT '主键ID，唯一标识每条聊天记录',
    channel     VARCHAR(255) COMMENT '沟通渠道，如QQ、彭博社等',
    trader      VARCHAR(255) COMMENT '交易员',
    user        VARCHAR(255) COMMENT '用户',
    duration    VARCHAR(255) COMMENT '持续时间',
    start_time  VARCHAR(255) COMMENT '开始时间',
    sender      VARCHAR(255) COMMENT '发送人',
    receiver    VARCHAR(255) COMMENT '接收人',
    message TEXT COMMENT '具体的消息详情',
    creator     VARCHAR(255) COMMENT '创建人',
    create_time VARCHAR(255) COMMENT '创建时间',
    updater     VARCHAR(255) COMMENT '更新者',
    update_time VARCHAR(255) COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT ='聊天数据';

-- 向表中插入数据，包括开始时间
INSERT INTO test.stg_message (id, channel, trader, user, duration, start_time, sender, receiver, message, creator,
                              create_time, updater, update_time)
VALUES (UNIX_TIMESTAMP(), 'qq', '张三', 'zhangs1(张三)', '00:05:23', '2023-12-01 10:00:00', '张三', '李四',
        '你好，我是张三，你有什么需求吗？', '张三', '2023-12-01 10:00:00', '张三', '2023-12-01 10:00:00'),
       (UNIX_TIMESTAMP() + 1, 'bloomberg', '王五', 'wangw1(王五)', '00:03:45', '2023-12-10 11:00:00', '赵六', '王五',
        '我想买一些股票，你有什么推荐吗？', '赵六', '2023-12-10 11:00:00', '赵六', '2023-12-10 11:00:00'),
       (UNIX_TIMESTAMP() + 2, 'em', '李七', 'liq1(李七)', '00:07:12', '2023-12-20 12:00:00', '李七', '王八',
        '你好，我是李七，你是王八吗？', '李七', '2023-12-20 12:00:00', '李七', '2023-12-20 12:00:00');

-- 选择并展示所有记录
SELECT *
FROM test.stg_message;

-- 删除已存在的表
DROP TABLE IF EXISTS test.stg_voice_recognition;

-- 创建新表
CREATE TABLE test.stg_voice_recognition
(
    id               VARCHAR(255) NOT NULL COMMENT '主键ID，唯一标识每条记录',
    file_name        VARCHAR(255) COMMENT '文件名',
    file_path        VARCHAR(255) NOT NULL COMMENT '文件路径',
    common_params    TEXT COMMENT '常用参数',
    file_size        VARCHAR(255) COMMENT '文件大小',
    duration         VARCHAR(255) COMMENT '文件时长',
    recognition_time VARCHAR(255) COMMENT '识别时间',
    content          TEXT COMMENT '识别内容',
    creator          VARCHAR(255) COMMENT '创建者',
    create_time      VARCHAR(255) COMMENT '创建时间',
    updater          VARCHAR(255) COMMENT '更新者',
    update_time      VARCHAR(255) COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT ='语音识别记录';

-- 向表中插入数据，包括识别时间
INSERT INTO test.stg_voice_recognition (id, file_name, file_path, common_params, file_size, duration, recognition_time,
                                        content, creator, create_time, updater, update_time)
VALUES (UNIX_TIMESTAMP(), '测试音频1', './audio/测试音频1.wav', '参数1', '2MB', '00:00:59', '2023-12-01 10:00:00',
        '示例内容1', '创建者A', '2023-12-01 00:00:00', '更新者A', '2023-12-01 00:00:00'),
       (UNIX_TIMESTAMP() + 1, '测试音频2', './audio/测试音频1.wav', '参数2', '4MB', '00:01:49', '2023-12-10 11:00:00',
        '未识别', '创建者B', '2023-12-10 00:00:00', '更新者B', '2023-12-10 00:00:00'),
       (UNIX_TIMESTAMP() + 2, '测试音频3', './audio/测试音频1.mp3', '参数3', '6MB', '00:06:03', '2023-12-20 12:00:00',
        '示例内容3', '创建者C', '2023-12-20 00:00:00', '更新者C', '2023-12-20 00:00:00');

-- 选择并展示所有记录
SELECT *
FROM test.stg_voice_recognition;

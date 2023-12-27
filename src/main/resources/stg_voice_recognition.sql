DROP TABLE IF EXISTS test.stg_voice_recognition;
CREATE TABLE test.stg_voice_recognition
(
    session_id  VARCHAR(255) COMMENT '会话ID，唯一标识每条记录',
    file_path   VARCHAR(255) NOT NULL COMMENT '文件名称',
    file_size   VARCHAR(255) COMMENT '文件大小',
    duration    VARCHAR(255) COMMENT '文件时长',
    content     TEXT COMMENT '识别内容',
    creator     VARCHAR(255) COMMENT '创建者',
    create_time VARCHAR(255) COMMENT '创建时间',
    updater     VARCHAR(255) COMMENT '更新者',
    update_time VARCHAR(255) COMMENT '更新时间',
    PRIMARY KEY (session_id)
) COMMENT ='语音识别记录';

INSERT INTO test.stg_voice_recognition (session_id, file_path, file_size, duration, content, creator,
                                        create_time, updater, update_time)
VALUES ('1', '测试音频1.wav', '2MB', '120s', '示例内容1', '创建者A', '2023-12-01 00:00:00', '更新者A',
        '2023-12-01 00:00:00'),
       ('2', '测试音频2.wav', '4MB', '180s', '示例内容2', '创建者B', '2023-12-10 00:00:00', '更新者B',
        '2023-12-10 00:00:00'),
       ('3', '测试音频3.wav', '6MB', '90s', '示例内容3', '创建者C', '2023-12-20 00:00:00', '更新者C',
        '2023-12-20 00:00:00');

SELECT *
FROM test.stg_voice_recognition;

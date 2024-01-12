DROP TABLE IF EXISTS test.stg_corpora;
CREATE TABLE test.stg_corpora
(
    id          VARCHAR(255) NOT NULL COMMENT '主键ID，唯一标识每条记录',
    name        VARCHAR(255) NOT NULL COMMENT '语料名称',
    type        VARCHAR(255) COMMENT '语料类型',
    annotation  TEXT COMMENT '语料标注',
    creator     VARCHAR(255) COMMENT '创建者',
    create_time VARCHAR(255) COMMENT '创建时间',
    updater     VARCHAR(255) COMMENT '更新者',
    update_time VARCHAR(255) COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT ='语料库';

INSERT INTO test.stg_corpora (id, name, type, annotation, creator, create_time, updater, update_time)
VALUES (UNIX_TIMESTAMP(), '语料1', '类型A', '语料1的注释', '创建者A', '2023-12-01 00:00:00', '更新者A',
        '2023-12-01 00:00:00'),
       (UNIX_TIMESTAMP() + 1, '语料2', '类型B', '语料2的注释', '创建者B', '2023-12-10 00:00:00', '更新者B',
        '2023-12-10 00:00:00'),
       (UNIX_TIMESTAMP() + 3, '语料3', '类型C', '语料3的注释', '创建者C', '2023-12-20 00:00:00', '更新者C',
        '2023-12-20 00:00:00');

SELECT *
FROM test.stg_corpora;
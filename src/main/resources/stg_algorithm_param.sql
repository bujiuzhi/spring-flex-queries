-- 删除表
DROP TABLE IF EXISTS test.stg_algorithm_param;

-- 创建表
CREATE TABLE test.stg_algorithm_param
(
    algorithm_id         VARCHAR(255) NOT NULL COMMENT '算法ID，唯一标识算法配置',
    algorithm_name       VARCHAR(255) COMMENT '算法名称，描述算法的标识',
    algorithm_desc       TEXT COMMENT '算法描述，提供算法的详细信息',
    algorithm_type       VARCHAR(255) COMMENT '算法类型，描述算法的分类',
    algorithm_parameters TEXT COMMENT '算法参数，以JSON格式存储算法的具体配置',
    model_id VARCHAR(255) COMMENT '模型的ID',
    creator              VARCHAR(255) COMMENT '创建者',
    creation_time        DATETIME COMMENT '创建时间',
    updater              VARCHAR(255) COMMENT '更新者',
    update_time          DATETIME COMMENT '更新时间',
    PRIMARY KEY (algorithm_id)
) COMMENT ='存储模型算法配置信息的表';

-- 插入数据
INSERT INTO test.stg_algorithm_param
(algorithm_id, algorithm_name, algorithm_desc, algorithm_type, algorithm_parameters,
 model_id, creator, creation_time, updater, update_time)
VALUES ('alg001', 'K-MEANS', 'K-MEANS 聚类算法', '聚类算法', '{"clusters": 5, "iterations": 100}',
        'MFSFMK202309270001', '张三', '2023-01-01 01:00:00', '李四', '2023-01-10 14:00:00'),
       ('alg002', 'CLARANS', 'CLARANS 聚类算法', '聚类算法', '{"num_local": 2, "max_neighbor": 100}',
        'MFSFMK202309270001', '王五', '2023-02-01 09:30:00', '赵六', '2023-02-15 16:30:00'),
       ('alg003', 'CURE', 'CURE 聚类算法', '聚类算法',
        '{"number_of_clusters": 3, "number_of_representatives": 5}',
        'MFSFMK202309270001', '孙七', '2023-03-01 10:00:00', '周八', '2023-03-20 17:00:00'),
       ('alg004', 'DBSCAN', 'DBSCAN 聚类算法', '聚类算法', '{"eps": 0.5, "min_samples": 5}',
        'MFSFMK202309270001', '刘九', '2023-04-01 12:00:00', '吴十', '2023-04-25 18:00:00');


-- 查询数据
SELECT *
FROM test.stg_algorithm_param;




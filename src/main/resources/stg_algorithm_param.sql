-- 删除表
DROP TABLE IF EXISTS stg_algorithm_param;

-- 创建表
CREATE TABLE stg_algorithm_param
(
    algorithm_id         VARCHAR(255) NOT NULL COMMENT '算法ID，唯一标识算法配置',
    algorithm_name       VARCHAR(255) COMMENT '算法名称，描述算法的标识',
    algorithm_desc       TEXT COMMENT '算法描述，提供算法的详细信息',
    algorithm_type       VARCHAR(255) COMMENT '算法类型，描述算法的分类',
    algorithm_parameters TEXT COMMENT '算法参数，以JSON格式存储算法的具体配置',
    creator              VARCHAR(255) COMMENT '创建者',
    creation_time        DATETIME COMMENT '创建时间',
    updater              VARCHAR(255) COMMENT '更新者',
    update_time          DATETIME COMMENT '更新时间',
    PRIMARY KEY (algorithm_id)
) COMMENT ='存储模型算法配置信息的表';

-- 插入数据
INSERT INTO stg_algorithm_param (algorithm_id, algorithm_name, algorithm_desc, algorithm_type, algorithm_parameters,
                                 creator, creation_time, updater, update_time)
VALUES ('alg001', 'k-means', 'k-means聚类算法', '聚类算法', '{"clusters": 5, "iterations": 100}',
        '张三', '2023-01-01 01:00:00', '李四', '2023-01-10 14:00:00'),
       ('alg002', 'logistic_regression', '逻辑回归算法', '分类算法', '{"C": 1.0, "max_iter": 200}',
        '王五', '2023-02-01 09:30:00', '赵六', '2023-02-15 16:30:00'),
       ('alg003', 'neural_network', '神经网络算法', '分类算法', '{"layers": [64, 32, 16], "epochs": 10}',
        '孙七', '2023-03-01 10:00:00', '周八', '2023-03-20 17:00:00'),
       ('alg004', 'decision_tree', '决策树算法', '分类算法', '{"depth": 10, "min_samples_split": 20}',
        '刘九', '2023-04-01 12:00:00', '吴十', '2023-04-25 18:00:00'),
       ('alg005', 'svm', '支持向量机算法', '分类算法', '{"kernel": "rbf", "C": 1.0}',
        '陈十一', '2023-05-01 13:00:00', '林十二', '2023-05-30 19:00:00');

-- 查询数据
SELECT *
FROM stg_algorithm_param;
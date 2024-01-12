DROP TABLE IF EXISTS test.pt_comm_server_tran;

CREATE TABLE test.pt_comm_server_tran
(
    `TRAN_ID`         varchar(60) NOT NULL COMMENT '流水号/交易流水',
    `SERVER_ID`       varchar(12) COMMENT '渠道ID',
    `TRAN_CODE`       varchar(200) COMMENT '交易码',
    `TRAN_DESC`       varchar(2000) COMMENT '交易描述',
    `TRAN_START_TIME` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `TRAN_END_TIME`   timestamp   NOT NULL DEFAULT '2017-09-27 14:30:45' COMMENT '结束时间',
    `TRAN_IN_MSG`     mediumtext COMMENT '交易请求报文',
    `TRAN_OUT_MSG`    mediumtext COMMENT '交易请求代码',
    `TRAN_STATUS`     varchar(8) COMMENT '交易结果 交易成功：SUCC 交易错误ERROR 交易超时TIMEOUT'
);

INSERT INTO test.pt_comm_server_tran (`TRAN_ID`, `SERVER_ID`, `TRAN_CODE`, `TRAN_DESC`, `TRAN_START_TIME`,
                                      `TRAN_END_TIME`, `TRAN_IN_MSG`, `TRAN_OUT_MSG`, `TRAN_STATUS`)
VALUES (CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '001'), 'S001', 'C001', '交易描述1', CURRENT_TIMESTAMP,
        '2017-09-27 14:30:45', '交易请求报文1', '交易响应报文1', 'SUCC'),
       (CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '002'), 'S002', 'C002', '交易描述2', CURRENT_TIMESTAMP,
        '2017-09-27 14:35:45', '交易请求报文2', '交易响应报文2', 'ERROR'),
       (CONCAT(DATE_FORMAT(CURDATE(), '%Y%m%d'), '003'), 'S003', 'C003', '交易描述3', CURRENT_TIMESTAMP,
        '2017-09-27 14:40:45', '交易请求报文3', '交易响应报文3', 'TIMEOUT');

SELECT *
FROM test.pt_comm_server_tran;

package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * StgMessage 实体类
 * 对应数据库中的 stg_message 表。
 * 此表用于存储聊天数据，包括沟通渠道、交易员、用户、持续时间、开始时间、发送人、接收人、消息详情等信息。
 * 每条记录包含主键ID、创建人、创建时间、更新人、更新时间等信息。
 */
@Data
public class StgMessage {

    private String id; // 主键ID

    private String channel; // 沟通渠道，如QQ、彭博社等

    private String trader; // 交易员

    private String user; // 用户

    private String duration; // 持续时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime; // 开始时间

    private String sender; // 发送人

    private String receiver; // 接收人

    private String message; // 具体的消息详情

    private String creator; // 创建人

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime; // 创建时间

    private String updater; // 更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime; // 更新时间
}

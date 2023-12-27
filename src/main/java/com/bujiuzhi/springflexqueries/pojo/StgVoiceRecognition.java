package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * StgVoiceRecognition 实体类
 * 对应数据库中的 stg_voice_recognition 表。
 * 此表用于存储语音识别的记录，包括文件信息和识别出的文本内容。
 * 每条记录包含会话ID、文件名称、文件大小、文件时长、识别日期、识别内容等信息。
 */
@Data
public class StgVoiceRecognition {

    private String id; // 主键ID

    @NotBlank(message = "文件名称路径不能为空")
    private String filePath; // 文件名称

    private String fileSize; // 文件大小
    private String duration; // 文件时长

    private String content; // 识别内容

    private String creator; // 创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime; // 创建时间

    private String updater; // 更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime; // 更新时间
}

package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;

import java.util.UUID;

/**
 * IatUtil 类用于调用讯飞语音识别接口。
 * 该类中的方法用于调用讯飞语音识别接口，将语音文件转换为文本。
 * 暂时返回测试数据
 */
public class IatUtil {
    public static StgVoiceRecognition start(String filePath) {
        StgVoiceRecognition stgVoiceRecognition = new StgVoiceRecognition();

        // 为StgVoiceRecognition对象设置测试数据
        stgVoiceRecognition.setSessionId(UUID.randomUUID().toString()); // 随机生成会话ID
        stgVoiceRecognition.setFilePath(filePath); // 模拟文件路径
        stgVoiceRecognition.setFileSize("15MB"); // 模拟文件大小
        stgVoiceRecognition.setDuration("3m 45s"); // 模拟文件时长
        stgVoiceRecognition.setContent("这是识别出的文本内容。"); // 模拟识别内容

        return stgVoiceRecognition;
    }

}

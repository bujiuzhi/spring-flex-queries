package com.bujiuzhi.springflexqueries.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * AudioConvert 类用于将MP3文件转换为WAV格式。
 */
public class AudioConvert {

    // 方法：将MP3文件转换为WAV格式
    public static File convertToWav(File sourceFile) {
        String sourceFileName = sourceFile.getAbsolutePath();
        // 结果wav文件放到/Users/bujiu/Downloads/audio/wav目录下
        String wavFileName = sourceFileName.replace(".mp3", ".wav").replace("mp3", "wav");
        // 若wav目录不存在
        File wavDir = new File("/Users/bujiu/Downloads/audio/wav");
        if (!wavDir.exists()) {
            // 创建wav目录
            wavDir.mkdirs();
        }

        try (AudioInputStream sourceStream = AudioSystem.getAudioInputStream(sourceFile)) {
            AudioFormat sourceFormat = sourceStream.getFormat();
            AudioFormat pcmFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(),
                    16,
                    sourceFormat.getChannels(),
                    sourceFormat.getChannels() * 2,
                    sourceFormat.getSampleRate(),
                    false
            );

            try (AudioInputStream pcmStream = AudioSystem.getAudioInputStream(pcmFormat, sourceStream)) {
                File wavFile = new File(wavFileName);
                AudioSystem.write(pcmStream, AudioFileFormat.Type.WAVE, wavFile);
                return wavFile;
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println("读取或转换MP3文件时出错: " + e.getMessage());
            return null;
        }
    }

    // 方法：获取并返回音频文件信息
    public static Map<String, String> getAudioFileInfo(String filePath) {
        Map<String, String> fileInfo = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            fileInfo.put("Error", "文件未找到: " + filePath);
            return fileInfo;
        }

        try {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat format = fileFormat.getFormat();

            // 使用中文描述音频参数
            String commonParams = "通道数: " + format.getChannels()
                    + ", 编码类型: " + format.getEncoding()
                    + ", 采样率: " + format.getSampleRate() + " Hz"
                    + ", 每个样本的位数: " + format.getSampleSizeInBits() + " bits"
                    + ", 帧大小: " + format.getFrameSize() + " bytes"
                    + ", 帧率: " + format.getFrameRate() + " frames/second";
            fileInfo.put("CommonParams", commonParams);

            long fileSizeInBytes = Files.size(Paths.get(file.getAbsolutePath()));
            // 将文件大小转换为MB并保留两位小数
            double fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);
            fileInfo.put("FileSize", String.format("%.2f MB", fileSizeInMB));

            long frameLength = fileFormat.getFrameLength();
            double durationInSeconds = (double) frameLength / format.getFrameRate();
            // 将文件时长转换为时分秒格式
            String durationFormatted = formatDuration(durationInSeconds);
            fileInfo.put("Duration", durationFormatted);
        } catch (UnsupportedAudioFileException | IOException e) {
            fileInfo.put("Error", "处理音频文件时出错: " + e.getMessage());
        }

        return fileInfo;
    }

    // 辅助方法：将秒转换为时分秒格式
    private static String formatDuration(double durationInSeconds) {
        int hours = (int) durationInSeconds / 3600;
        int minutes = (int) (durationInSeconds % 3600) / 60;
        int seconds = (int) durationInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    // 主方法
    public static void main(String[] args) {
        String sourceFilePath = "/Users/bujiu/Downloads/test.mp3";
        File sourceFile = new File(sourceFilePath);

        if (!sourceFile.exists()) {
            System.out.println("文件不存在，请检查路径：" + sourceFilePath);
            return;
        }

        // 打印并存储转换前的文件信息
        System.out.println("转换前的文件信息：");
        Map<String, String> fileInfoBefore = getAudioFileInfo(sourceFilePath);
        fileInfoBefore.forEach((key, value) -> System.out.println(key + ": " + value));

        try {
            System.out.println("\n开始转换...");
            File wavFile = convertToWav(sourceFile);
            System.out.println("转换完成。WAV文件路径: " + wavFile.getAbsolutePath());

            // 打印并存储转换后的文件信息
            System.out.println("\n转换后的文件信息：");
            Map<String, String> fileInfoAfter = getAudioFileInfo(wavFile.getAbsolutePath());
            fileInfoAfter.forEach((key, value) -> System.out.println(key + ": " + value));

        } catch (Exception e) {
            System.err.println("转换过程中出现错误: " + e.getMessage());
        }
    }
}

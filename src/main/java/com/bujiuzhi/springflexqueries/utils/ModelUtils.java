package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import org.json.JSONException;
import org.json.JSONObject;

public class ModelUtils {

    /**
     * 触发预定任务。
     *
     * @param stgModelJob 模型作业实体
     * @return 触发结果的描述
     */
    public static String triggerJob(StgModelJob stgModelJob) {
        // 实现触发逻辑
        // 以下为示例实现，需要根据实际业务逻辑进行调整
        return "事务 " + stgModelJob.getJobId() + " 已触发模型作业，模型信息：" + stgModelJob.toString();
    }

    /**
     * 查询状态。
     *
     * @param stgModelJob 模型作业实体
     * @return 状态查询结果的描述
     */
    public static String queryStatus(StgModelJob stgModelJob) {
        // 实现查询逻辑
        // 以下为示例实现，需要根据实际业务逻辑进行调整
        JSONObject statusJson = new JSONObject();

        // 示例状态值，实际逻辑中应根据业务需求确定状态值
        try {
            statusJson.put("ExecRslt", "200");
            statusJson.put("BsnRecptCmnt", "运行成功");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return statusJson.toString();
    }

    /**
     * 下载日志文件。
     *
     * @param stgModelJob 模型作业实体
     * @return 日志下载结果的描述
     */
    public static String downloadJournalLog(StgModelJob stgModelJob) {
        // 实现下载逻辑
        // 以下为示例实现，需要根据实际业务逻辑进行调整
        return "事务 " + stgModelJob.getJobId() + " 已下载模型作业日志，模型信息：" + stgModelJob.toString();
    }

    /**
     * 停止作业。
     *
     * @param stgModelJob 模型作业实体
     * @return 停止作业结果的描述
     */
    public static String stopJob(StgModelJob stgModelJob) {
        // 实现停止逻辑
        // 以下为示例实现，需要根据实际业务逻辑进行调整
        return "事务 " + stgModelJob.getJobId() + " 已停止模型作业，模型信息：" + stgModelJob.toString();
    }

    /**
     * 根据模型ID和模型版本生成唯一ID。
     * 此方法会自动使用当前时间戳。
     *
     * @param modelId      模型ID，将会被转换为小写形式。
     * @param modelVersion 模型版本，将会去除其中的小数点。
     * @return 生成的唯一ID字符串。
     */
    public static String generateJobId(String modelId, String modelVersion) {
        // 将模型ID转换为小写
        String lowercaseModelId = modelId.toLowerCase();

        // 移除模型版本中的小数点
        String versionWithoutDots = modelVersion.replace(".", "dot");

        // 获取当前时间的时间戳
        long timestamp = System.currentTimeMillis();

        // 组合模型ID、模型版本和时间戳生成唯一ID
        String uniqueId = timestamp + "-" + lowercaseModelId + "-" + versionWithoutDots;

        return uniqueId;
    }

}

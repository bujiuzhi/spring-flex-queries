package com.bujiuzhi.springflexqueries.scheduler;

import com.bujiuzhi.springflexqueries.mapper.ModelMapper;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.utils.ModelUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 模型状态更新器，用于定期更新模型作业的状态和持续时间。
 */
@Component
public class ModelStatusUpdater {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 定时任务，每60秒执行一次。
     * 用于定期检查并更新“运行中”的模型作业的状态。
     */
    @Scheduled(fixedDelay = 60000)
    public void updateModelStatusAndDuration() {
        // 查询所有“运行中”的模型作业
        List<StgModelJob> runningJobs = modelMapper.findRunningJobs();

        for (StgModelJob job : runningJobs) {
            // 使用ModelUtils的queryStatus方法获取最新状态
            String jsonStatus = ModelUtils.queryStatus(job);
            JSONObject statusObj = null;
            String currentStatus = null;
            try {
                statusObj = new JSONObject(jsonStatus);
                // 获取模型作业的当前状态
                currentStatus = statusObj.getString("BsnRecptCmnt");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // 根据不同状态设置持续时间
            if ("运行成功".equals(currentStatus)) {
                job.setTrainingProgress(100.0F);
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Duration duration = Duration.between(LocalDateTime.parse(job.getLastTrainingTime(), dateTimeFormatter), now);
                // 计算天数、小时数、分钟数和秒数
                long days = duration.toDays();
                long hours = duration.toHours() % 24;
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;
                job.setLastTrainingDuration(days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒");
                job.setUpdater("定时任务更新模型状态");
                job.setUpdateTime(now.format(dateTimeFormatter));
            } else if ("运行失败".equals(currentStatus) || "没有状态".equals(currentStatus)) {
                job.setTrainingProgress(0.0F);
                job.setLastTrainingDuration("异常"); // 使用特定的异常值表示持续时间
            }

            job.setModelStatus(currentStatus);
            modelMapper.update(job, "test.stg_model_job");
        }
    }
}

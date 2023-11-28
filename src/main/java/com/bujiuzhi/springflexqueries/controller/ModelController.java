package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/models")
@Validated
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * 处理搜索模型作业的POST请求。
     *
     * @param searchRequest 客户端发送的包含搜索条件的请求体
     * @return 返回操作结果，封装在Result对象中。如果操作成功，Result对象包含模型作业数据列表；如果操作失败，包含相应的错误信息。
     */
    @PostMapping("/search")
    public Result search(@Valid @RequestBody SearchRequest searchRequest) {
        // 调用modelService的search方法处理搜索请求，并返回结果
        return modelService.search(searchRequest);
    }

    /**
     * 根据作业ID获取模型作业的详情。
     *
     * @param jobId 作业ID
     * @return 返回操作结果封装对象，包含模型作业详情或错误信息
     */
    @PostMapping("/findBy")
    public Result findBy(@RequestParam String jobId) {
        return modelService.findBy(jobId);
    }

    /**
     * 新增一个模型作业记录。
     *
     * @param stgModelJob 模型作业对象，包含所需新增信息
     * @return 返回操作结果封装对象，包含成功或错误信息
     */
    @PostMapping("/insert")
    public Result insert(@Validated(StgModelJob.Insert.class) @RequestBody StgModelJob stgModelJob) {
        return modelService.insert(stgModelJob);
    }

    /**
     * 更新指定的模型作业信息。
     *
     * @param stgModelJob 模型作业对象，包含更新信息
     * @return 返回操作结果封装对象，包含成功或错误信息
     */
    @PostMapping("/update")
    public Result update(@Validated(StgModelJob.Update.class) @RequestBody StgModelJob stgModelJob) {
        return modelService.update(stgModelJob);
    }

    /**
     * 获取所有算法名称。
     *
     * @return 操作结果，包含算法名称列表或错误信息
     */
    @PostMapping("/getAllAlgorithmNames")
    public Result getAllAlgorithmNames() {
        return modelService.getAllAlgorithmNames();
    }

    /**
     * 根据模型算法名称获取配置信息。
     *
     * @param stgAlgorithmParam 模型算法实体类
     * @return 返回操作结果封装对象，包含模型算法配置信息或错误信息
     */
    @PostMapping("/getAlgorithmParam")
    public Result getAlgorithmParam(@Valid @RequestBody StgAlgorithmParam stgAlgorithmParam) {
        return modelService.getAlgorithmParam(stgAlgorithmParam);
    }

    /**
     * 触发模型作业。
     * 根据传入的事务ID和模型作业实体，执行作业触发操作，并返回操作结果。
     *
     * @param stgModelJob 模型作业实体
     * @return Result     操作结果，包含成功或失败的消息
     */
    @PostMapping("/triggerJob")
    public Result triggerJob(@Validated({StgModelJob.Update.class, StgModelJob.Insert.class}) @RequestBody StgModelJob stgModelJob) {
        return modelService.triggerJob(stgModelJob);
    }

    /**
     * 查询模型作业状态。
     * 根据传入的事务ID和模型作业实体，查询作业的当前状态，并返回状态信息。
     *
     * @param stgModelJob 模型作业实体
     * @return Result     操作结果，包含作业状态信息或失败的消息
     */
    @PostMapping("/queryJobStatus")
    public Result queryJobStatus(@Validated({StgModelJob.Update.class, StgModelJob.Insert.class}) @RequestBody StgModelJob stgModelJob) {
        return modelService.queryJobStatus(stgModelJob);
    }

    /**
     * 下载模型作业日志。
     * 根据传入的事务ID和模型作业实体，执行日志下载操作，并返回操作结果。
     *
     * @param stgModelJob 模型作业实体
     * @return Result     操作结果，包含下载日志的消息或失败的消息
     */
    @PostMapping("/downloadJobJournal")
    public Result downloadJobJournal(@Validated(StgModelJob.Update.class) @RequestBody StgModelJob stgModelJob) {
        return modelService.downloadJobJournal(stgModelJob);
    }

    /**
     * 停止模型作业。
     * 根据传入的事务ID和模型作业实体，执行作业停止操作，并返回操作结果。
     *
     * @param stgModelJob 模型作业实体
     * @return Result     操作结果，包含停止作业的消息或失败的消息
     */
    @PostMapping("/stopJob")
    public Result stopJob(@Validated({StgModelJob.Update.class, StgModelJob.Insert.class}) @RequestBody StgModelJob stgModelJob) {
        return modelService.stopJob(stgModelJob);
    }

}

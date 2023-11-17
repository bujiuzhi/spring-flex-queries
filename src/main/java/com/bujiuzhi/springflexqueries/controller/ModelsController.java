package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.ModelJob;
import com.bujiuzhi.springflexqueries.pojo.ModelJobManagerInfo;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.service.ModelsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// ModelsController类负责处理模型相关的HTTP请求。
@RestController
@RequestMapping("/models")
@Validated
public class ModelsController {

    @Autowired
    private ModelsService modelsService;

    /**
     * 根据给定的条件执行模型作业的搜索操作。
     *
     * @param modelJobManagerInfo 一个封装了ModelJob对象和附加信息的复合对象，
     *                            ModelJob对象包含了搜索条件，而附加信息如分页和排序则包含在Map中。
     * @return Result 搜索操作的结果，包含一个模型作业列表或错误信息。
     */
    @PostMapping("/search")
    public Result search(@RequestBody ModelJobManagerInfo modelJobManagerInfo) {
        ModelJob modelJob = modelJobManagerInfo.getModelJob();
        Map<String, Object> additionalData = modelJobManagerInfo.getAdditionalData();
        return modelsService.search(modelJob, additionalData);
    }

    /**
     * 管理模型作业的添加或更新操作。
     * 先检查ModelJob对象中的model_id和model_version字段，
     * 根据这些字段以及数据库中的记录决定是新增记录还是更新记录。
     *
     * @param modelJobManagerInfo 一个封装了ModelJob对象和附加信息的复合对象，
     *                            其中ModelJob对象包含了需要添加或更新的数据。
     * @return Result 操作的结果，表明是成功添加、成功更新还是操作失败的信息。
     */
    @PostMapping("/manage")
    public Result manage(@RequestBody @Valid ModelJobManagerInfo modelJobManagerInfo) {
        ModelJob modelJob = modelJobManagerInfo.getModelJob();
        Map<String, Object> additionalData = modelJobManagerInfo.getAdditionalData();
        return modelsService.manage(modelJob, additionalData);
    }

}

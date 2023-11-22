package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 管理模型作业的设置，包括添加或更新记录。
     *
     * @param stgModelJob 客户端发送的模型作业对象
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/manage")
    public Result manage(@Valid @RequestBody StgModelJob stgModelJob) {
        return modelService.manage(stgModelJob);
    }

}

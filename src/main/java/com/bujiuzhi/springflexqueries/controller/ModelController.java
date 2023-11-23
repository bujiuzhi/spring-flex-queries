package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
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


    /**
     * 通过POST请求根据模型ID和版本获取模型作业详情。
     *
     * @param stgModelJob 模型作业对象
     * @return 返回操作结果封装对象，包含模型作业详情或错误信息
     */
    @PostMapping("/findBy")
    public Result findBy(@Valid @RequestBody StgModelJob stgModelJob) {
        return modelService.findBy(stgModelJob);
    }

    /**
     * 通过POST请求更新指定的模型作业信息。
     *
     * @param stgModelJob 模型作业对象，包含更新信息
     * @return 返回操作结果封装对象，包含成功或错误信息
     */
    @PostMapping("/update")
    public Result update(@Valid @RequestBody StgModelJob stgModelJob) {
        return modelService.update(stgModelJob);
    }

    /**
     * 通过POST请求新增一个模型作业记录。
     *
     * @param stgModelJob 模型作业对象，包含所需新增信息
     * @return 返回操作结果封装对象，包含成功或错误信息
     */
    @PostMapping("/insert")
    public Result insert(@Valid @RequestBody StgModelJob stgModelJob) {
        return modelService.insert(stgModelJob);
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
     * 获取所有算法名称。
     *
     * @return 操作结果，包含算法名称列表或错误信息
     */
    @PostMapping("/getAllAlgorithmNames")
    public Result getAllAlgorithmNames() {
        return modelService.getAllAlgorithmNames();
    }

}

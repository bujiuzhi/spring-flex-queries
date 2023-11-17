package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.ModelJob;
import com.bujiuzhi.springflexqueries.pojo.Result;

import java.util.Map;

// ModelsService接口定义了与模型作业相关的业务逻辑操作。
public interface ModelsService {

    /**
     * 根据ModelJob对象和其他附加信息进行搜索。
     *
     * @param modelJob       ModelJob对象，包含搜索条件的属性。
     * @param additionalData 包含额外搜索信息的Map，如分页参数、排序方式等。
     * @return Result 返回一个Result对象，包含搜索操作的结果。
     */
    Result search(ModelJob modelJob, Map<String, Object> additionalData);

    /**
     * 检查并执行模型作业的添加或更新操作。
     * 如果模型ID和版本号在数据库中已存在，则执行更新操作（自动升级版本号）；
     * 如果模型ID存在但版本号不同，则直接更新；
     * 如果模型ID不存在，则执行插入新记录的操作。
     *
     * @param modelJob       模型作业对象，包含模型的详细信息。
     * @param additionalData 包含额外操作信息的Map，比如操作用户等。
     * @return Result         返回操作的结果，包含成功或失败的详细信息。
     */
    Result manage(ModelJob modelJob, Map<String, Object> additionalData);

}



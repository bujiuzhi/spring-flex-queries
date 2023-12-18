package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;

public interface ModelService {

    /**
     * 根据属性名获取该属性的不同值。
     *
     * @param attributeName 属性名
     * @return 返回操作结果，封装在Result对象中。
     */
    Result getAttributeValues(String attributeName);

    /**
     * 根据搜索请求进行数据查询。
     *
     * @param searchRequest 包含搜索条件的请求对象
     * @return 返回搜索结果的Result对象。如果搜索成功，Result对象包含数据列表；如果失败，包含错误信息。
     */
    Result search(SearchRequest searchRequest);

    /**
     * 通过作业ID查询模型作业详情。
     *
     * @param jobId 作业ID
     * @return 操作结果封装对象，包含模型作业详情或错误信息
     */
    Result findBy(String jobId);

    /**
     * 查询所有处于“运行中”状态的模型作业。
     *
     * @return 返回包含查询结果的Result对象。如果没有查询到数据，返回错误信息。
     */
    public Result findRunningJobs();

    /**
     * 新增模型作业记录。
     *
     * @param stgModelJob 包含新增信息的模型作业对象
     * @return 封装操作结果的对象，包含成功或错误信息
     */
    Result insert(StgModelJob stgModelJob);

    /**
     * 更新模型作业信息。
     *
     * @param stgModelJob 包含更新信息的模型作业对象
     * @return 封装操作结果的对象，包含成功或错误信息
     */
    Result update(StgModelJob stgModelJob);

    /**
     * 获取所有算法名称，并封装在Result对象中。
     *
     * @return 封装了操作结果的Result对象
     */
    Result getAllAlgorithmNames();

    /**
     * 根据模型算法名称获取配置信息，并封装在Result对象中。
     *
     * @param stgAlgorithmParam 模型算法实体类
     * @return 封装了操作结果的Result对象
     */
    Result getAlgorithmParam(StgAlgorithmParam stgAlgorithmParam);

    /**
     * 触发模型作业。
     *
     * @param stgModelJob 模型作业实体
     * @return 操作结果
     */
    Result triggerJob(StgModelJob stgModelJob);

    /**
     * 查询模型作业状态。
     *
     * @param stgModelJob 模型作业实体
     * @return 状态信息
     */
    Result queryJobStatus(StgModelJob stgModelJob);

    /**
     * 下载模型作业日志。
     *
     * @param stgModelJob 模型作业实体
     * @return 日志信息
     */
    Result downloadJobJournal(StgModelJob stgModelJob);

    /**
     * 停止模型作业。
     *
     * @param stgModelJob 模型作业实体
     * @return 操作结果
     */
    Result stopJob(StgModelJob stgModelJob);


}



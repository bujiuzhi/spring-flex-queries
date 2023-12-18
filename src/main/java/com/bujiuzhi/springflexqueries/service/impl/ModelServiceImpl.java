package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.ModelMapper;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.service.ModelService;
import com.bujiuzhi.springflexqueries.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 根据指定的属性名查询该属性的所有不同非空值。
     * 此方法从数据库中检索指定属性的所有唯一值，并过滤掉其中的空值。
     *
     * @param attributeName 属性名
     * @return 返回操作结果，封装在Result对象中。
     */
    @Override
    public Result getAttributeValues(String attributeName) {
        if (attributeName == null || attributeName.trim().isEmpty()) {
            return Result.error("属性名不能为空");
        }

        List<String> values = modelMapper.getAttributeValues(attributeName);
        // 过滤掉列表中的null值
        values = values.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (values.isEmpty()) {
            return Result.error("没有找到对应属性的非空值");
        }
        return Result.success(values);
    }


    /**
     * 根据给定的搜索请求进行数据库查询。
     *
     * @param searchRequest 搜索请求参数，包含过滤条件
     * @return 返回包含查询结果的Result对象。如果没有查询到数据，返回错误信息。
     */
    @Override
    public Result search(SearchRequest searchRequest) {
        // 通过modelMapper调用search方法，传入搜索条件和表名，获取查询结果列表
        // 使用分页参数进行查询
        int pageNumber = searchRequest.getPageNumber() != null ? searchRequest.getPageNumber() : 1;
        int pageSize = searchRequest.getPageSize() != null ? searchRequest.getPageSize() : 10;
        searchRequest.setPageNumber(pageNumber);
        searchRequest.setPageSize(pageSize);
        List<StgModelJob> stgModelJobs = modelMapper.search(searchRequest, "test.stg_model_job");

        // 判断查询结果是否为空，如果是，则返回一个错误信息的Result对象
        if (stgModelJobs.isEmpty()) {
            return Result.error("没有找到符合条件的模型作业");
        }

        System.out.println(stgModelJobs);

        // 如果查询结果不为空，返回包含查询结果的Result对象

        // 获取满足条件的总数据量
        int totalRecords = modelMapper.count(searchRequest, "test.stg_model_job");

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalRecords / searchRequest.getPageSize());

        // 将分页信息放入Map中
        Map<String, Object> paginationInfo = new HashMap<>();
        paginationInfo.put("pageNumber", searchRequest.getPageNumber());
        paginationInfo.put("pageSize", searchRequest.getPageSize());
        paginationInfo.put("totalRecords", totalRecords);
        paginationInfo.put("totalPages", totalPages);

        // 创建包含数据和分页信息的Map
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", stgModelJobs);
        responseData.put("pagination", paginationInfo);

        // 返回Result对象，包含responseData
        return Result.success(responseData);

    }

    /**
     * 通过作业ID查询模型作业详情。
     *
     * @param jobId 作业ID
     * @return 操作结果封装对象，包含模型作业详情或错误信息
     */
    @Override
    public Result findBy(String jobId) {
        StgModelJob job = modelMapper.findBy(jobId);
        if (job != null) {
            return Result.success(job);
        } else {
            return Result.error("未找到对应的模型作业");
        }
    }

    /**
     * 查询所有处于“运行中”状态的模型作业。
     *
     * @return 返回包含查询结果的Result对象。如果没有查询到数据，返回错误信息。
     */
    @Override
    public Result findRunningJobs() {
        List<StgModelJob> stgModelJobs = modelMapper.findRunningJobs();
        if (stgModelJobs == null || stgModelJobs.isEmpty()) {
            return Result.error("没有运行中的模型作业");
        }
        return Result.success(stgModelJobs);
    }

    /**
     * 新增模型作业记录。
     *
     * @param stgModelJob 包含新增信息的模型作业对象
     * @return 封装操作结果的对象，包含成功或错误信息
     */
    @Override
    public Result insert(StgModelJob stgModelJob) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String jobId = ModelUtils.generateJobId(stgModelJob.getModelId(), stgModelJob.getModelVersion());
        stgModelJob.setJobId(jobId);
//        stgModelJob.setCreationTime(LocalDateTime.now());
        stgModelJob.setCreationTime(now.format(dateTimeFormatter));
        stgModelJob.setLastTrainingTime(now.format(dateTimeFormatter));
        stgModelJob.setModelStatus("运行中");
        stgModelJob.setTrainingProgress(50.0F);

        // 执行插入操作
        int insertCount = modelMapper.insert(stgModelJob, "test.stg_model_job");
        if (insertCount > 0) {
            // 插入成功
            String triggerJobResult = ModelUtils.triggerJob(stgModelJob);
            return Result.success("模型作业添加成功，且已触发，返回信息如下：" + triggerJobResult);
            //触发job
        } else {
            // 插入失败，可能是数据违反了约束或其他数据库操作问题
            return Result.error("模型作业添加失败");
        }
    }


    /**
     * 更新模型作业信息。
     *
     * @param stgModelJob 包含更新信息的模型作业对象
     * @return 封装操作结果的对象，包含成功或错误信息
     */
    @Override
    public Result update(StgModelJob stgModelJob) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 检查是否存在匹配的模型ID和版本
        StgModelJob existingJob = modelMapper.findBy(stgModelJob.getJobId());
        if (existingJob == null) {
            // 如果不存在匹配的记录，返回错误信息
            return Result.error("该jobId未在数据库中发现匹配的作业");
        }

        // 存在匹配的记录，执行更新操作
        stgModelJob.setUpdateTime(now.format(dateTimeFormatter));
        int updateCount = modelMapper.update(stgModelJob, "test.stg_model_job");
        if (updateCount > 0) {
            // 更新成功
            return Result.success("模型作业更新成功");
        } else {
            // 更新失败
            return Result.error("模型作业更新失败");
        }
    }

    /**
     * 获取所有算法名称，并封装在Result对象中。
     *
     * @return 封装了操作结果的Result对象
     */
    @Override
    public Result getAllAlgorithmNames() {
        List<String> names = modelMapper.getAllAlgorithmNames();
        if (names == null || names.isEmpty()) {
            return Result.error("没有找到算法名称");
        }
        return Result.success(names);
    }

    /**
     * 根据模型算法名称获取配置信息。
     *
     * @param stgAlgorithmParam 模型算法实体类
     * @return 封装了操作结果的Result对象
     */
    @Override
    public Result getAlgorithmParam(StgAlgorithmParam stgAlgorithmParam) {
        StgAlgorithmParam algorithm = modelMapper.getAlgorithmParam(stgAlgorithmParam.getAlgorithmName());
        if (algorithm != null) {
            return Result.success(algorithm);
        }
        return Result.error("未查询到对应的模型算法配置信息");
    }

    /**
     * 触发模型作业。
     *
     * @param stgModelJob 模型作业实体
     * @return 操作结果
     */
    @Override
    public Result triggerJob(StgModelJob stgModelJob) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        stgModelJob.setLastTrainingTime(now.format(dateTimeFormatter));
        stgModelJob.setUpdater("job重训练");
        stgModelJob.setUpdateTime(now.format(dateTimeFormatter));
        modelMapper.update(stgModelJob, "test.stg_model_job");
        String message = ModelUtils.triggerJob(stgModelJob);
        return Result.success(message);
    }

    /**
     * 查询模型作业状态。
     *
     * @param stgModelJob 模型作业实体
     * @return 状态信息
     */
    @Override
    public Result queryJobStatus(StgModelJob stgModelJob) {
        String message = ModelUtils.queryStatus(stgModelJob);
        return Result.success(message);
    }

    /**
     * 下载模型作业日志。
     *
     * @param stgModelJob 模型作业实体
     * @return 日志信息
     */
    @Override
    public Result downloadJobJournal(StgModelJob stgModelJob) {
        String message = ModelUtils.downloadJournalLog(stgModelJob);
        return Result.success(message);
    }

    /**
     * 停止模型作业。
     *
     * @param stgModelJob 模型作业实体
     * @return 操作结果
     */
    @Override
    public Result stopJob(StgModelJob stgModelJob) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        stgModelJob.setUpdater("job停止");
        stgModelJob.setUpdateTime(now.format(dateTimeFormatter));
        stgModelJob.setModelStatus("运行失败");
        modelMapper.update(stgModelJob, "test.stg_model_job");
        String message = ModelUtils.triggerJob(stgModelJob);
        return Result.success(message);
    }
}



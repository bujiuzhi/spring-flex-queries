package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.ModelMapper;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

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

        // 如果查询结果不为空，返回包含查询结果的Result对象
        return Result.success(stgModelJobs);
    }

    /**
     * 管理模型作业的设置，实现新增或更新逻辑。
     *
     * @param stgModelJob 模型作业对象
     * @return 返回操作结果，封装在Result对象中。
     */
    @Override
    public Result manage(StgModelJob stgModelJob) {
        //先对modelId和modelVersion进行非空判断
//        if (stgModelJob.getModelId() == null || stgModelJob.getModelVersion() == null) {
//            return Result.error("模型ID和模型版本不能为空");
//        }

        // 检查数据库中是否存在匹配的模型作业
        StgModelJob existingJob = modelMapper.findBy(stgModelJob.getModelId(), stgModelJob.getModelVersion());
        if (existingJob != null) {
            // 如果存在，进行更新操作
            int updateCount = modelMapper.update(stgModelJob, "test.stg_model_job");
            if (updateCount > 0) {
                return Result.success("模型作业更新成功");
            } else {
                return Result.error("模型作业更新失败");
            }
        } else {
            // 如果不存在，进行新增操作
            int insertCount = modelMapper.insert(stgModelJob, "test.stg_model_job");
            if (insertCount > 0) {
                return Result.success("模型作业添加成功");
            } else {
                return Result.error("模型作业添加失败");
            }
        }
    }

    /**
     * 根据模型ID和版本查询模型作业详情。
     *
     * @param stgModelJob 模型作业对象
     * @return 封装操作结果的对象，包含模型作业详情或错误信息
     */
    @Override
    public Result findBy(StgModelJob stgModelJob) {
        //先对modelId和modelVersion进行非空判断
//        if (stgModelJob.getModelId() == null || stgModelJob.getModelVersion() == null) {
//            return Result.error("模型ID和模型版本不能为空");
//        }

        // 查询模型作业
        StgModelJob job = modelMapper.findBy(stgModelJob.getModelId(), stgModelJob.getModelVersion());
        if (job != null) {
            // 查询成功，返回模型作业详情
            return Result.success(job);
        } else {
            // 查询失败，返回错误信息
            return Result.error("未找到对应的模型作业");
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
        //先对modelId和modelVersion进行非空判断
//        if (stgModelJob.getModelId() == null || stgModelJob.getModelVersion() == null) {
//            return Result.error("模型ID和模型版本不能为空");
//        }

        // 检查是否存在匹配的模型ID和版本
        StgModelJob existingJob = modelMapper.findBy(stgModelJob.getModelId(), stgModelJob.getModelVersion());
        if (existingJob == null) {
            // 如果不存在匹配的记录，返回错误信息
            return Result.error("没有找到匹配的模型ID和版本");
        }

        // 存在匹配的记录，执行更新操作
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
     * 新增模型作业记录。
     *
     * @param stgModelJob 包含新增信息的模型作业对象
     * @return 封装操作结果的对象，包含成功或错误信息
     */
    @Override
    public Result insert(StgModelJob stgModelJob) {
        //先对modelId和modelVersion进行非空判断
//        if (stgModelJob.getModelId() == null || stgModelJob.getModelVersion() == null) {
//            return Result.error("模型ID和模型版本不能为空");
//        }

        // 检查数据库中是否已存在具有相同模型ID和模型版本的记录
        StgModelJob existingJob = modelMapper.findBy(stgModelJob.getModelId(), stgModelJob.getModelVersion());
        if (existingJob != null) {
            // 如果存在重复记录，返回错误信息
            return Result.error("模型作业已存在，无法添加重复的模型ID和版本");
        }

        // 不存在重复记录，执行插入操作
        int insertCount = modelMapper.insert(stgModelJob, "test.stg_model_job");
        if (insertCount > 0) {
            // 插入成功
            return Result.success("模型作业添加成功");
        } else {
            // 插入失败，可能是数据违反了约束或其他数据库操作问题
            return Result.error("模型作业添加失败");
        }
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

}



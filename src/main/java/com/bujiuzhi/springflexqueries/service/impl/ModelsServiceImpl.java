package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.ModelsMapper;
import com.bujiuzhi.springflexqueries.pojo.ModelJob;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// ModelsServiceImpl类实现了ModelsService接口，提供了模型数据的业务逻辑处理。
@Service
public class ModelsServiceImpl implements ModelsService {

    @Autowired
    private ModelsMapper modelsMapper;

    /**
     * 根据ModelJob对象和其他附加信息进行模型作业的搜索操作。
     *
     * @param modelJob       包含搜索条件的ModelJob对象。
     * @param additionalData 包含额外搜索信息的Map，如分页参数、排序方式等。
     * @return Result         返回搜索操作的结果，包含一个模型作业列表或错误信息。
     */
    @Override
    public Result search(ModelJob modelJob, Map<String, Object> additionalData) {
        List<ModelJob> modelJobs = modelsMapper.search(modelJob);
        if (modelJobs.isEmpty()) {
            return Result.error("没有找到符合条件的模型作业");
        }
        return Result.success(modelJobs);
    }

    /**
     * 检查并执行模型作业的添加或更新操作。
     * 如果模型ID和版本号在数据库中已存在，则执行更新操作（自动升级版本号）；
     * 如果模型ID存在但版本号不同，则直接更新；
     * 如果模型ID不存在，则执行插入新记录的操作。
     *
     * @param modelJob       包含要管理的模型作业信息的ModelJob对象。
     * @param additionalData 包含额外操作信息的Map。
     * @return Result         返回操作的结果，包含成功或失败的详细信息。
     */
    @Override
    public Result manage(ModelJob modelJob, Map<String, Object> additionalData) {
//        // 检查模型ID和版本号是否已存在
//        if (modelJob.getModel_id() == null || modelJob.getModel_version() == null) {
//            return Result.error("模型ID和版本号不能为空");
//        }

        // 查找现有模型作业
        ModelJob existingModelJob = findById(modelJob.getModel_id());

        // 如果存在现有模型作业
        if (existingModelJob != null) {
            // 如果版本号匹配，自动升级版本号并更新
            if (existingModelJob.getModel_version().equals(modelJob.getModel_version())) {
                String newVersion = incrementVersion(existingModelJob.getModel_version());
                modelJob.setModel_version(newVersion);
                return update(modelJob);
            } else {
                // 如果版本号不匹配，直接更新
                return update(modelJob);
            }
        } else {
            // 如果不存在现有模型作业，执行新增操作
            return add(modelJob);
        }
    }

    private Result add(ModelJob modelJob) {
        // 将ModelJob对象插入数据库中
        int insertCount = modelsMapper.insert(modelJob);
        if (insertCount > 0) {
            return Result.success("模型作业添加成功");
        } else {
            return Result.error("模型作业添加失败");
        }
    }

    private Result update(ModelJob modelJob) {
        // 更新数据库中的模型作业记录
        int updateCount = modelsMapper.update(modelJob);
        if (updateCount > 0) {
            return Result.success("模型作业更新成功");
        } else {
            return Result.error("模型作业更新失败");
        }
    }

    private ModelJob findById(String modelId) {
        //在数据库中按ID查找模型作业。
        return modelsMapper.findById(modelId);
    }

    private String incrementVersion(String currentVersion) {
        // 去掉版本字符串中的 'v'，并分割数字部分。
        String[] parts = currentVersion.substring(1).split("\\.");
        int major = Integer.parseInt(parts[0]); // 主版本号
        int minor = Integer.parseInt(parts[1]); // 次版本号

        // 假设我们自增次版本号
        minor++;
        // 构建新的版本号字符串
        return "v" + major + "." + minor;
    }

}



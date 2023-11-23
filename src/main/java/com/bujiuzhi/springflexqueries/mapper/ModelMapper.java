package com.bujiuzhi.springflexqueries.mapper;

import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgAlgorithmParam;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import com.bujiuzhi.springflexqueries.utils.ModelSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ModelMapper {

    /**
     * 使用ModelSqlProvider类的search方法动态生成SQL语句，并执行查询操作。
     *
     * @param searchRequest 包含搜索条件的对象，用于生成SQL语句中的where条件。
     * @param tableName     查询的目标数据库表名，用于指定查询操作的数据表。
     * @return 返回查询结果列表，每个结果项为StgModelJob类型的实例。
     */
    @SelectProvider(type = ModelSqlProvider.class, method = "search")
    List<StgModelJob> search(SearchRequest searchRequest, String tableName);

    /**
     * 根据模型ID和模型版本查找模型作业。
     *
     * @param modelId      模型的唯一标识ID。
     * @param modelVersion 模型的版本号。
     * @return 返回对应的模型作业对象。
     */
    @Select("SELECT * FROM test.stg_model_job WHERE model_id = #{modelId} AND model_version = #{modelVersion}")
    StgModelJob findBy(String modelId, String modelVersion);

    /**
     * 更新模型作业信息。
     *
     * @param stgModelJob 需要更新的模型作业对象。
     * @param tableName   目标数据库表名。
     * @return 返回更新操作影响的行数。
     */
    @UpdateProvider(type = ModelSqlProvider.class, method = "update")
    int update(StgModelJob stgModelJob, String tableName);

    /**
     * 插入新的模型作业记录到数据库。
     *
     * @param stgModelJob 新的模型作业对象。
     * @param tableName   目标数据库表名。
     * @return 返回插入操作影响的行数。
     */
    @InsertProvider(type = ModelSqlProvider.class, method = "insert")
    int insert(StgModelJob stgModelJob, String tableName);

    /**
     * 根据模型算法名称查询对应的配置信息。
     *
     * @param algorithmName 模型算法名称
     * @return 对应的模型算法配置信息
     */
    @Select("SELECT * FROM test.stg_algorithm_param WHERE algorithm_name = #{algorithmName}")
    StgAlgorithmParam getAlgorithmParam(String algorithmName);

    /**
     * 查询所有算法名称。
     *
     * @return 算法名称列表
     */
    @Select("SELECT algorithm_name FROM test.stg_algorithm_param")
    List<String> getAllAlgorithmNames();

}

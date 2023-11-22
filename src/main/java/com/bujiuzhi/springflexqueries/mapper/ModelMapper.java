package com.bujiuzhi.springflexqueries.mapper;

import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
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

    @Select("SELECT * FROM test.stg_model_job WHERE model_id = #{modelId} AND model_version = #{modelVersion}")
    StgModelJob findByModelIdAndVersion(String modelId, String modelVersion);

    @UpdateProvider(type = ModelSqlProvider.class, method = "update")
    int update(StgModelJob stgModelJob, String tableName);

    @InsertProvider(type = ModelSqlProvider.class, method = "insert")
    int insert(StgModelJob stgModelJob, String tableName);

}

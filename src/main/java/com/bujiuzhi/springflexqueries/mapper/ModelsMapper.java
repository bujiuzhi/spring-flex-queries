package com.bujiuzhi.springflexqueries.mapper;

import com.bujiuzhi.springflexqueries.pojo.ModelJob;
import com.bujiuzhi.springflexqueries.utils.ModelsSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

// ModelsMapper接口定义了数据库操作的方法，这些方法通过MyBatis框架实现与数据库的交互。
@Mapper
public interface ModelsMapper {

    /**
     * 根据模型ID查询模型作业的详细信息。
     *
     * @param modelId 模型作业的唯一标识符。
     * @return 返回与modelId匹配的ModelJob对象，如果没有找到则返回null。
     */
    @Select("SELECT * FROM test.model_jobs WHERE model_id = #{model_id}")
    ModelJob findById(String modelId);

    /**
     * 根据ModelJob对象的属性搜索匹配的模型作业记录。
     * 使用动态SQL构建搜索条件，支持多条件查询。
     *
     * @param modelJob 包含搜索条件属性的ModelJob对象。
     * @return 返回匹配搜索条件的ModelJob列表，如果没有匹配的记录则返回空列表。
     */
    @SelectProvider(type = ModelsSqlProvider.class, method = "search")
    List<ModelJob> search(ModelJob modelJob);

    /**
     * 将ModelJob对象的属性插入数据库，创建新的模型作业记录。
     * 使用动态SQL根据ModelJob中的属性构建插入语句。
     *
     * @param modelJob 要插入数据库的ModelJob对象。
     * @return 返回插入操作影响的行数，通常是1。
     */
    @InsertProvider(type = ModelsSqlProvider.class, method = "insert")
    int insert(ModelJob modelJob);

    /**
     * 更新数据库中的现有模型作业记录。
     * 使用动态SQL根据ModelJob中的属性构建更新语句，model_id用于匹配记录。
     *
     * @param modelJob 包含更新信息的ModelJob对象。
     * @return 返回更新操作影响的行数，通常是1，如果没有更新任何记录则是0。
     */
    @UpdateProvider(type = ModelsSqlProvider.class, method = "update")
    int update(ModelJob modelJob);

}

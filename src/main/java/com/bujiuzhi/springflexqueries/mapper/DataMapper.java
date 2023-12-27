package com.bujiuzhi.springflexqueries.mapper;

import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import com.bujiuzhi.springflexqueries.utils.DataSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 数据访问接口
 * 用于执行针对语音文件记录和语料库记录的数据库操作。
 */
@Mapper
public interface DataMapper {

    /**
     * 根据日期范围搜索语音文件记录。
     * 使用动态SQL生成方法。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 语音文件记录列表
     */
    @SelectProvider(type = DataSqlProvider.class, method = "searchVoiceRecords")
    List<StgVoiceRecognition> searchVoiceRecords(String startDate, String endDate, int pageNumber, int pageSize);

    /**
     * 计算符合条件的语音文件记录总数。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 记录总数
     */
    @SelectProvider(type = DataSqlProvider.class, method = "countVoiceRecords")
    int countVoiceRecords(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 插入语音文件记录。
     *
     * @param stgVoiceRecognition 语音文件记录对象
     * @return 返回插入的记录数
     */
    @InsertProvider(type = DataSqlProvider.class, method = "insertVoiceRecord")
    int insertVoiceRecord(StgVoiceRecognition stgVoiceRecognition);

    /**
     * 计算符合条件的语料库记录总数。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param creator   上传人
     * @return 记录总数
     */
    @SelectProvider(type = DataSqlProvider.class, method = "countCorporaRecords")
    int countCorporaRecords(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("creator") String creator);

    /**
     * 根据日期范围和上传人搜索语料库记录。
     * 使用动态SQL生成方法。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param creator   上传人
     * @return 语料库记录列表
     */
    @SelectProvider(type = DataSqlProvider.class, method = "searchCorporaRecords")
    List<StgCorpora> searchCorporaRecords(String startDate, String endDate, String creator, int pageNumber, int pageSize);

    /**
     * 插入新的语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 返回插入的记录数
     */
    @InsertProvider(type = DataSqlProvider.class, method = "insertCorpora")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertCorpora(StgCorpora stgCorpora);

    /**
     * 更新语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 返回更新的记录数
     */
    @UpdateProvider(type = DataSqlProvider.class, method = "updateCorpora")
    int updateCorpora(StgCorpora stgCorpora);

    /**
     * 删除指定的语料库记录。
     *
     * @param id 语料库记录ID
     */
    @Delete("DELETE FROM test.stg_corpora WHERE id = #{id}")
    void deleteCorpora(@Param("id") String id);

}

package com.bujiuzhi.springflexqueries.mapper;

import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import com.bujiuzhi.springflexqueries.utils.DataSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 * 用于执行针对语音文件记录和语料库记录的数据库操作。
 */
@Mapper
public interface DataMapper {

    /**
     * 根据提供的参数搜索语音文件记录。
     *
     * @param params 包含搜索参数的Map
     * @return 符合条件的语音文件记录列表
     */
    @SelectProvider(type = DataSqlProvider.class, method = "searchVoiceRecords")
    List<StgVoiceRecognition> searchVoiceRecords(Map<String, Object> params);

    /**
     * 计算符合条件的语音文件记录总数。
     *
     * @param params 包含搜索参数的Map
     * @return 符合条件的记录总数
     */
    @SelectProvider(type = DataSqlProvider.class, method = "countVoiceRecords")
    int countVoiceRecords(Map<String, Object> params);

    /**
     * 插入语音文件记录
     * @param stgVoiceRecognition 语音文件记录对象
     */
    @InsertProvider(type = DataSqlProvider.class, method = "insertVoiceRecord")
    void insertVoiceRecord(StgVoiceRecognition stgVoiceRecognition);

    /**
     * 根据ID查找语音文件记录
     *
     * @param id 语音文件记录的ID
     * @return 语音文件记录
     */
    @SelectProvider(type = DataSqlProvider.class, method = "findVoiceRecordById")
    StgVoiceRecognition findVoiceRecordById(@Param("id") String id);

    /**
     * 更新语音文件记录
     *
     * @param stgVoiceRecognition 更新后的语音文件记录对象
     */
    @UpdateProvider(type = DataSqlProvider.class, method = "updateVoiceRecord")
    void updateVoiceRecord(StgVoiceRecognition stgVoiceRecognition);

    /**
     * 删除指定的语音文件记录
     *
     * @param id 语音文件记录的ID
     */
    @DeleteProvider(type = DataSqlProvider.class, method = "deleteVoiceRecord")
    void deleteVoiceRecord(@Param("id") String id);

    /**
     * 根据文件路径删除语音文件记录
     *
     * @param filePath 文件路径
     */
    @Delete("DELETE FROM test.stg_voice_recognition WHERE file_path LIKE CONCAT('%', #{filePath}, '%')")
    void deleteVoiceRecordByFilePath(@Param("filePath") String filePath);

    /**
     * 根据多个条件搜索语料库记录。
     *
     * @param params 包含搜索参数的Map
     * @return 符合条件的语料库记录列表
     */
    @SelectProvider(type = DataSqlProvider.class, method = "searchCorporaRecords")
    List<StgCorpora> searchCorporaRecords(Map<String, Object> params);

    /**
     * 计算符合条件的语料库记录总数。
     *
     * @param params 包含搜索参数的Map
     * @return 符合条件的记录总数
     */
    @SelectProvider(type = DataSqlProvider.class, method = "countCorporaRecords")
    int countCorporaRecords(Map<String, Object> params);

    /**
     * 插入新的语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 返回插入的记录数
     */
    @InsertProvider(type = DataSqlProvider.class, method = "insertCorpora")
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

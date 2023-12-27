package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;

/**
 * 数据服务接口
 * 用于处理语音文件记录和语料库记录的业务逻辑。
 */
public interface DataService {

    /**
     * 根据识别日期搜索语音文件记录。
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param pageNumber 页码
     * @param pageSize   页大小
     * @return 操作结果，包含分页的语音文件记录
     */
    Result searchVoiceRecords(String startDate, String endDate, int pageNumber, int pageSize);

    /**
     * 新增语音文件记录
     *
     * @param filePath 语音文件路径
     * @return 操作结果(识别出该文件的信息)
     */
    Result saveVoiceRecord(String filePath);

    /**
     * 根据上传日期和上传人搜索语料库记录。
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param creator    上传人
     * @param pageNumber 页码
     * @param pageSize   页大小
     * @return 操作结果，包含分页的语料库记录
     */
    Result searchCorporaRecords(String startDate, String endDate, String creator, int pageNumber, int pageSize);

    /**
     * 新增语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 操作结果
     */
    Result saveCorpora(StgCorpora stgCorpora);

    /**
     * 更新语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 操作结果
     */
    Result updateCorpora(StgCorpora stgCorpora);

    /**
     * 删除指定的语料库记录。
     *
     * @param id 语料库记录ID
     * @return 操作结果
     */
    Result deleteCorpora(String id);

}

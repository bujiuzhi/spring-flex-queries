package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据服务接口
 * 用于处理语音文件记录和语料库记录的业务逻辑。
 */
public interface DataService {
    /**
     * 根据多个时间范围和其他条件搜索语音文件记录。
     *
     * @param startRecognitionTime 识别开始时间
     * @param endRecognitionTime 识别结束时间
     * @param startCreateTime 创建开始时间
     * @param endCreateTime 创建结束时间
     * @param fileName 文件名称
     * @param creator 创建者
     * @param updater 更新者
     * @param pageNumber 页码
     * @param pageSize 页数
     * @return 操作结果
     */
    Result searchVoiceRecords(String startRecognitionTime, String endRecognitionTime,
                              String startCreateTime, String endCreateTime,
                              String fileName, String creator, String updater,
                              int pageNumber, int pageSize);

    /**
     * 上传语音文件
     * @param file 上传的MultipartFile文件
     * @return 操作结果
     */
    Result uploadVoice(MultipartFile file);

    /**
     * 删除指定路径的文件。
     *
     * @param filePath 要删除的文件路径
     * @return 操作结果
     */
    Result deleteVoice(String filePath);

    /**
     * 保存语音文件记录
     *
     * @param stgVoiceRecognition 语音识别记录对象
     * @return 操作结果
     */
    Result saveVoiceRecord(StgVoiceRecognition stgVoiceRecognition);

    /**
     * 删除语音文件记录
     *
     * @param id 语音记录的唯一标识
     * @return 操作结果
     */
    Result deleteVoiceRecord(String id);

    /**
     * 更新语音文件记录
     * @param stgVoiceRecognition 更新后的语音识别记录对象
     * @return 操作结果
     */
    Result updateVoiceRecord(StgVoiceRecognition stgVoiceRecognition);

    /**
     * 进行语音识别
     * @param id 语音记录的唯一标识
     * @return 操作结果
     */
    Result recognizeVoice(String id);

    /**
     * 根据创建时间范围、名称、类型和更新者搜索语料库记录。
     *
     * @param startCreateTime 创建开始时间
     * @param endCreateTime   创建结束时间
     * @param name            语料名称
     * @param type            语料类型
     * @param creator         创建者
     * @param updater         更新者
     * @param pageNumber      页码
     * @param pageSize        页数
     * @return 操作结果
     */
    public Result searchCorporaRecords(String startCreateTime, String endCreateTime,
                                       String name, String type, String creator, String updater,
                                       int pageNumber, int pageSize);

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

    /**
     * 根据多个条件搜索消息记录。
     * 支持根据沟通渠道、交易员、用户、开始时间范围、发送人和接收人等条件进行搜索。
     *
     * @param channel        沟通渠道
     * @param trader         交易员
     * @param user           用户
     * @param startStartTime 开始时间的起始范围
     * @param endStartTime   开始时间的结束范围
     * @param sender         发送人
     * @param receiver       接收人
     * @param pageNumber     页码
     * @param pageSize       每页记录数
     * @return 返回操作结果，封装在Result对象中。
     */
    Result searchMessages(String channel, String trader, String user, String startStartTime, String endStartTime, String sender, String receiver, int pageNumber, int pageSize);

}

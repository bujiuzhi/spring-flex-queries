package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import com.bujiuzhi.springflexqueries.service.DataService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据控制器
 * 用于处理语音文件记录和语料库记录的请求。
 */
@RestController
@RequestMapping("/data")
@Validated
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * 根据多个时间范围和其他条件搜索语音文件记录。
     * 可以根据识别时间、创建时间、文件名称、创建者和更新者等进行搜索。
     *
     * @param startRecognitionTime 识别开始时间
     * @param endRecognitionTime   识别结束时间
     * @param startCreateTime      创建开始时间
     * @param endCreateTime        创建结束时间
     * @param fileName             文件名称
     * @param creator              创建者
     * @param updater              更新者
     * @param pageNumber           页码
     * @param pageSize             页数
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/searchVoiceRecords")
    public Result searchVoiceRecords(@RequestParam(required = false) String startRecognitionTime,
                                     @RequestParam(required = false) String endRecognitionTime,
                                     @RequestParam(required = false) String startCreateTime,
                                     @RequestParam(required = false) String endCreateTime,
                                     @RequestParam(required = false) String fileName,
                                     @RequestParam(required = false) String creator,
                                     @RequestParam(required = false) String updater,
                                     @RequestParam(defaultValue = "1") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        return dataService.searchVoiceRecords(startRecognitionTime, endRecognitionTime, startCreateTime, endCreateTime, fileName, creator, updater, pageNumber, pageSize);
    }

    /**
     * 上传语音文件接口
     * 如果是mp3格式，将转换为wav格式
     * 文件名相同的情况下，新上传的文件将覆盖旧文件
     *
     * @param file 上传的文件
     * @return 返回操作结果
     */
    @PostMapping("/uploadVoice")
    public Result uploadVoice(@RequestParam("file") MultipartFile file) {
        return dataService.uploadVoice(file);
    }

    /**
     * 根据文件路径删除语音文件及其数据库记录。。
     *
     * @param filePath 要删除的文件路径
     * @return 返回操作结果
     */
    @PostMapping("/deleteVoice")
    public Result deleteVoice(@RequestParam("filePath") String filePath) {
        return dataService.deleteVoice(filePath);
    }

    /**
     * 保存语音文件记录接口
     *
     * @param stgVoiceRecognition 语音识别记录对象
     * @return 返回操作结果
     */
    @PostMapping("/saveVoiceRecord")
    public Result saveVoiceRecord(@RequestBody StgVoiceRecognition stgVoiceRecognition) {
        return dataService.saveVoiceRecord(stgVoiceRecognition);
    }

    /**
     * 删除语音文件记录接口
     * 同时删除对应的音频文件，如果存在mp3文件也会一同删除
     *
     * @param id 要删除的记录的ID
     * @return 返回操作结果
     */
    @PostMapping("/deleteVoiceRecord")
    public Result deleteVoiceRecord(@RequestParam String id) {
        return dataService.deleteVoiceRecord(id);
    }

    /**
     * 更新语音文件记录接口
     *
     * @param stgVoiceRecognition 更新后的语音识别记录对象
     * @return 返回操作结果
     */
    @PostMapping("/updateVoiceRecord")
    public Result updateVoiceRecord(@RequestBody StgVoiceRecognition stgVoiceRecognition) {
        return dataService.updateVoiceRecord(stgVoiceRecognition);
    }

    /**
     * 语音识别接口
     * 接收一个语音文件的ID，调用IatUtil.start进行模拟的语音识别，并更新记录
     *
     * @param id 语音文件的ID
     * @return 返回操作结果
     */
    @PostMapping("/recognizeVoice")
    public Result recognizeVoice(@RequestParam String id) {
        return dataService.recognizeVoice(id);
    }


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
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/searchCorporaRecords")
    public Result searchCorporaRecords(@RequestParam(required = false) String startCreateTime,
                                       @RequestParam(required = false) String endCreateTime,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) String creator,
                                       @RequestParam(required = false) String updater,
                                       @RequestParam(defaultValue = "1") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize) {
        return dataService.searchCorporaRecords(startCreateTime, endCreateTime, name, type, creator, updater, pageNumber, pageSize);
    }

    /**
     * 新增语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/saveCorpora")
    public Result saveCorpora(@Validated(StgCorpora.Insert.class) @RequestBody StgCorpora stgCorpora) {
        return dataService.saveCorpora(stgCorpora);
    }

    /**
     * 更新语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/updateCorpora")
    public Result updateCorpora(@Validated(StgCorpora.Update.class) @RequestBody StgCorpora stgCorpora) {
        return dataService.updateCorpora(stgCorpora);
    }

    /**
     * 删除指定的语料库记录。
     *
     * @param id 语料库记录ID
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/deleteCorpora")
    public Result deleteCorpora(@Valid @NotBlank @RequestParam String id) {
        return dataService.deleteCorpora(id);
    }

    /**
     * 根据多个条件搜索消息记录。
     * 支持通过沟通渠道、交易员、用户、开始时间范围、发送人和接收人等条件进行搜索。
     * 还包括分页功能。
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
    @PostMapping("/searchMessages")
    public Result searchMessages(@RequestParam(required = false) String channel,
                                 @RequestParam(required = false) String trader,
                                 @RequestParam(required = false) String user,
                                 @RequestParam(required = false) String startStartTime,
                                 @RequestParam(required = false) String endStartTime,
                                 @RequestParam(required = false) String sender,
                                 @RequestParam(required = false) String receiver,
                                 @RequestParam(defaultValue = "1") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        return dataService.searchMessages(channel, trader, user, startStartTime, endStartTime, sender, receiver, pageNumber, pageSize);
    }

}

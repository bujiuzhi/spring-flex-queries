package com.bujiuzhi.springflexqueries.controller;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
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
     * 文件上传，保存在本地
     *
     * @param file
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        return dataService.upload(file);
    }

    /**
     * 根据识别日期搜索语音文件记录。
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param pageNumber 页码
     * @param pageSize   页数
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/searchVoiceRecords")
    public Result searchVoiceRecords(@RequestParam(required = false) String startDate,
                                     @RequestParam(required = false) String endDate,
                                     @RequestParam(defaultValue = "1") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize) {
        return dataService.searchVoiceRecords(startDate, endDate, pageNumber, pageSize);
    }

    /**
     * 新增语音文件记录
     *
     * @param filePath 语音文件路径
     * @return 返回操作结果(识别出该文件的信息)，封装在Result对象中。
     */
    @PostMapping("/saveVoiceRecord")
    public Result saveVoiceRecord(@Valid @NotBlank @RequestParam String filePath) {
        return dataService.saveVoiceRecord(filePath);
    }

    /**
     * 根据上传日期和上传人搜索语料库记录。
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param creator    上传人
     * @param pageNumber 页码
     * @param pageSize   页数
     * @return 返回操作结果，封装在Result对象中。
     */
    @PostMapping("/searchCorporaRecords")
    public Result searchCorporaRecords(@RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                                       @RequestParam(required = false) String creator,
                                       @RequestParam(defaultValue = "1") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize) {
        return dataService.searchCorporaRecords(startDate, endDate, creator, pageNumber, pageSize);
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
}

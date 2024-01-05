package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.DataMapper;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import com.bujiuzhi.springflexqueries.service.DataService;
import com.bujiuzhi.springflexqueries.utils.AudioConvert;
import com.bujiuzhi.springflexqueries.utils.IatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据服务实现类
 * 实现数据服务接口中定义的方法，处理语音文件和语料库记录的业务逻辑。
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    /**
     * 根据识别日期搜索语音文件记录。
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param pageNumber 页码
     * @param pageSize   页大小
     * @return 操作结果，包含分页的语音文件记录
     */
    @Override
    public Result searchVoiceRecords(String startDate, String endDate, int pageNumber, int pageSize) {
        List<StgVoiceRecognition> records = dataMapper.searchVoiceRecords(startDate, endDate, pageNumber, pageSize);
        if (records.isEmpty()) {
            return Result.error("没有找到符合条件的语音文件记录");
        }

        int totalRecords = dataMapper.countVoiceRecords(startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Map<String, Object> paginationInfo = new HashMap<>();
        paginationInfo.put("pageNumber", pageNumber);
        paginationInfo.put("pageSize", pageSize);
        paginationInfo.put("totalRecords", totalRecords);
        paginationInfo.put("totalPages", totalPages);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", records);
        responseData.put("pagination", paginationInfo);

        return Result.success(responseData);
    }

    /**
     * 上传语音文件
     *
     * @param file 上传的MultipartFile文件
     * @return 操作结果
     */
    @Override
    public Result uploadVoice(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传失败，文件为空");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String targetDirectory = fileExtension.equals("mp3") ? "/Users/bujiu/Downloads/audio/mp3" : "/Users/bujiu/Downloads/audio/wav";

            File targetFolder = new File(targetDirectory);
            if (!targetFolder.exists() && !targetFolder.mkdirs()) {
                throw new IOException("创建目标文件夹失败：" + targetDirectory);
            }

            File sourceFile = new File(targetFolder, originalFilename);
            file.transferTo(sourceFile);

            // 检查文件类型并转换
            File processedFile = fileExtension.equals("mp3")
                    ? AudioConvert.convertToWav(sourceFile)
                    : sourceFile;

            // 获取文件信息
            Map<String, String> fileInfo = AudioConvert.getAudioFileInfo(processedFile.getAbsolutePath());

            // 创建语音文件记录
            StgVoiceRecognition record = new StgVoiceRecognition();
            record.setId(generateId());
            record.setFileName(processedFile.getName());
            record.setFilePath(processedFile.getAbsolutePath());
            record.setFileSize(fileInfo.get("FileSize"));
            record.setDuration(fileInfo.get("Duration"));
            record.setCommonParams(fileInfo.get("CommonParams"));
            record.setContent("未识别");
            // ... 其他字段设置
            record.setCreator("admin");
            record.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            dataMapper.insertVoiceRecord(record);
            return Result.success("文件上传成功");
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }

    /**
     * 保存语音文件记录
     *
     * @param record 语音识别记录对象
     * @return 操作结果
     */
    @Override
    public Result saveVoiceRecord(StgVoiceRecognition record) {
        //先进行参数二次验证，防止validation不生效
//        if (record.getFilePath() == null) {
//            return Result.error("文件路径不能为空");
//        }
        dataMapper.insertVoiceRecord(record);
        return Result.success("记录保存成功");
    }

    /**
     * 删除语音文件记录
     *
     * @param id 语音记录的唯一标识
     * @return 操作结果
     */
    @Override
    public Result deleteVoiceRecord(String id) {
        //先进行参数二次验证，防止validation不生效
//        if (id == null || id.isEmpty()) {
//            return Result.error("主键ID不能为空");
//        }
        // 从数据库中获取记录，以便知道要删除的文件路径
        StgVoiceRecognition record = dataMapper.findVoiceRecordById(id);
        if (record != null) {
            try {
                // 删除文件
                Files.deleteIfExists(Paths.get(record.getFilePath()));

                // 删除数据库记录
                dataMapper.deleteVoiceRecord(id);
                return Result.success("记录删除成功");
            } catch (IOException e) {
                return Result.error("文件删除失败：" + e.getMessage());
            }
        }
        return Result.error("未找到指定ID的记录");
    }

    /**
     * 更新语音文件记录
     *
     * @param record 更新后的语音识别记录对象
     * @return 操作结果
     */
    @Override
    public Result updateVoiceRecord(StgVoiceRecognition record) {
        //先进行参数二次验证，防止validation不生效
//        if (record.getId() == null) {
//            return Result.error("主键ID不能为空");
//        }
        dataMapper.updateVoiceRecord(record);
        return Result.success("记录更新成功");
    }

    /**
     * 进行语音识别
     *
     * @param id 语音记录的唯一标识
     * @return 操作结果
     */
    @Override
    public Result recognizeVoice(String id) {
        //先进行参数二次验证，防止validation不生效
//        if (id == null || id.isEmpty()) {
//            return Result.error("主键ID不能为空");
//        }
        StgVoiceRecognition record = dataMapper.findVoiceRecordById(id);
        if (record != null && "未识别".equals(record.getContent())) {
            String recognitionResult = IatUtil.start(record.getFilePath());
            record.setContent(recognitionResult);
            record.setRecognitionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            record.setUpdater("admin");
            record.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            dataMapper.updateVoiceRecord(record);
            return Result.success("语音识别完成，内容已更新,识别结果为：" + recognitionResult);
        }
        return Result.error("找不到指定的记录或记录已识别");
    }

    /**
     * 将MultipartFile转换为File
     *
     * @param multipartFile MultipartFile文件
     * @param filename      文件名
     * @return 转换后的File对象
     * @throws IOException
     */
    private File convertToFile(MultipartFile multipartFile, String filename) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + filename);
        multipartFile.transferTo(file);
        return file;
    }

    /**
     * 生成语音文件记录的唯一标识
     *
     * @return 唯一标识
     */
    private String generateId() {
        // 根据具体逻辑生成ID
        return String.valueOf(System.currentTimeMillis());
    }

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
    @Override
    public Result searchCorporaRecords(String startDate, String endDate, String creator, int pageNumber, int pageSize) {
        List<StgCorpora> records = dataMapper.searchCorporaRecords(startDate, endDate, creator, pageNumber, pageSize);
        if (records.isEmpty()) {
            return Result.error("没有找到符合条件的语料库记录");
        }

        int totalRecords = dataMapper.countCorporaRecords(startDate, endDate, creator);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Map<String, Object> paginationInfo = new HashMap<>();
        paginationInfo.put("pageNumber", pageNumber);
        paginationInfo.put("pageSize", pageSize);
        paginationInfo.put("totalRecords", totalRecords);
        paginationInfo.put("totalPages", totalPages);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("list", records);
        responseData.put("pagination", paginationInfo);

        return Result.success(responseData);
    }

    /**
     * 新增语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 操作结果
     */
    @Override
    public Result saveCorpora(StgCorpora stgCorpora) {
//        //先进行参数二次验证，防止validation不生效
//        if (stgCorpora.getName() == null) {
//            return Result.error("语料名称不能为空");
//        }

        stgCorpora.setId(generateId()); // 生成唯一标识
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());

        stgCorpora.setCreateTime(currentTime); // 设置当前时间为更新时间
        // 假设creator和updater是固定的或者从上下文中获取
        stgCorpora.setCreator("admin");

        // 新增逻辑
        int i = dataMapper.insertCorpora(stgCorpora);
        if (i == 0) {
            return Result.error("新增失败");
        }
        return Result.success("新增成功");
    }

    /**
     * 更新语料库记录。
     *
     * @param stgCorpora 语料库记录对象
     * @return 操作结果
     */
    @Override
    public Result updateCorpora(StgCorpora stgCorpora) {
//        //先进行参数二次验证，防止validation不生效
//        if (stgCorpora.getId()==null){
//            return Result.error("主键ID不能为空");
//        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());

        stgCorpora.setUpdateTime(currentTime); // 设置当前时间为更新时间
        // 假设creator和updater是固定的或者从上下文中获取
        stgCorpora.setUpdater("admin");

        // 更新逻辑
        int i = dataMapper.updateCorpora(stgCorpora);
        if (i == 0) {
            return Result.error("更新失败");
        }
        return Result.success("更新成功");
    }

    /**
     * 删除指定的语料库记录。
     *
     * @param id 语料库记录ID
     * @return 操作结果
     */
    @Override
    public Result deleteCorpora(String id) {
//        //先进行参数二次验证，防止validation不生效
//        if (id == null || id.isEmpty()){
//            return Result.error("主键ID不能为空");
//        }

        // 删除语料库记录的逻辑实现
        dataMapper.deleteCorpora(id);
        return Result.success("删除成功");
    }
}
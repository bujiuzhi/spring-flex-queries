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
import java.nio.file.Path;
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
     * 根据多个时间范围和其他条件搜索语音文件记录。
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
     * @return 操作结果
     */
    @Override
    public Result searchVoiceRecords(String startRecognitionTime, String endRecognitionTime,
                                     String startCreateTime, String endCreateTime,
                                     String fileName, String creator, String updater,
                                     int pageNumber, int pageSize) {
        // 创建一个map来传递所有的搜索参数
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("startRecognitionTime", startRecognitionTime);
        searchParams.put("endRecognitionTime", endRecognitionTime);
        searchParams.put("startCreateTime", startCreateTime);
        searchParams.put("endCreateTime", endCreateTime);
        searchParams.put("fileName", fileName);
        searchParams.put("creator", creator);
        searchParams.put("updater", updater);
        searchParams.put("pageNumber", pageNumber);
        searchParams.put("pageSize", pageSize);

        // 从mapper调用搜索方法
        List<StgVoiceRecognition> records = dataMapper.searchVoiceRecords(searchParams);
        if (records.isEmpty()) {
            return Result.error("没有找到符合条件的语音文件记录");
        }

        int totalRecords = dataMapper.countVoiceRecords(searchParams);
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
            String mp3Directory = "/Users/bujiu/Downloads/audio/mp3";
            String wavDirectory = "/Users/bujiu/Downloads/audio/wav";

            File mp3Folder = new File(mp3Directory);
            File wavFolder = new File(wavDirectory);

            if ((!mp3Folder.exists() && !mp3Folder.mkdirs()) || (!wavFolder.exists() && !wavFolder.mkdirs())) {
                throw new IOException("创建目标文件夹失败");
            }

            // 将上传的文件保存到指定目录
            File sourceFile = new File(mp3Folder, originalFilename);
            file.transferTo(sourceFile);

            String filePaths = sourceFile.getAbsolutePath();

            // 如果是MP3文件，转换为WAV格式并拼接路径
            if (fileExtension.equals("mp3")) {
                File processedFile = AudioConvert.convertToWav(sourceFile);
                filePaths += ";" + processedFile.getAbsolutePath(); // 使用分号分隔两个路径
            }

            // 获取文件信息
            Map<String, String> fileInfo = AudioConvert.getAudioFileInfo(filePaths.split(";")[0]); // 使用第一个路径获取文件信息

            // 创建语音文件记录
            StgVoiceRecognition record = new StgVoiceRecognition();
            record.setId(generateId());
            record.setFileName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
            record.setFilePath(filePaths); // 存储拼接的路径
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
     * 根据文件路径删除语音文件及其数据库记录。
     *
     * @param filePath 要删除的文件路径
     * @return 操作结果
     */
    @Override
    public Result deleteVoice(String filePath) {
        try {
            // 删除相关文件
            Path fileToDelete = Paths.get(filePath);
            if (Files.exists(fileToDelete)) {
                Files.delete(fileToDelete);
            }
            //若以.mp3结尾还需删除.wav文件
            if (filePath.endsWith(".mp3")) {
                String wavFilePath = filePath.replace("mp3", "wav");
                Path wavFileToDelete = Paths.get(wavFilePath);
                if (Files.exists(wavFileToDelete)) {
                    Files.delete(wavFileToDelete);
                }
            }

            // 删除数据库中包含这个路径的所有记录
            // 注意：这里假设filePath是完整的文件名，包括扩展名
            dataMapper.deleteVoiceRecordByFilePath(filePath);

            return Result.success("语音文件及其记录删除成功");
        } catch (IOException e) {
            return Result.error("语音文件删除失败: " + e.getMessage());
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
    @Override
    public Result searchCorporaRecords(String startCreateTime, String endCreateTime,
                                       String name, String type, String creator, String updater,
                                       int pageNumber, int pageSize) {
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("startCreateTime", startCreateTime);
        searchParams.put("endCreateTime", endCreateTime);
        searchParams.put("name", name);
        searchParams.put("type", type);
        searchParams.put("creator", creator);
        searchParams.put("updater", updater);
        searchParams.put("pageNumber", pageNumber);
        searchParams.put("pageSize", pageSize);

        List<StgCorpora> records = dataMapper.searchCorporaRecords(searchParams);
        if (records.isEmpty()) {
            return Result.error("没有找到符合条件的语料库记录");
        }

        int totalRecords = dataMapper.countCorporaRecords(searchParams);
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
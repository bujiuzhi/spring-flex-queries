package com.bujiuzhi.springflexqueries.service.impl;

import com.bujiuzhi.springflexqueries.mapper.DataMapper;
import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import com.bujiuzhi.springflexqueries.service.DataService;
import com.bujiuzhi.springflexqueries.utils.IatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * 文件上传，保存在本地
     *
     * @param file
     * @return 操作结果
     */
    @Override
    public Result upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Result.error("上传失败，文件为空");
        }

        // 指定保存文件的路径
        String directory = "/Users/bujiu/Downloads/voice";
        Path path = Paths.get(directory);

        try {
            // 确保目录存在
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // 构造文件的完整路径
            Path filePath = path.resolve(file.getOriginalFilename());

            // 保存文件到本地
            file.transferTo(filePath.toFile());

            // 调用语音识别接口saveVoiceRecord，识别语音文件
            saveVoiceRecord(filePath.toString());

            return Result.success("上传成功，且已进行语音识别");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败，发生异常：" + e.getMessage());
        }
    }

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
     * 新增语音文件记录
     *
     * @param filePath 语音文件路径
     * @return 操作结果(识别出该文件的信息)
     */
    @Override
    public Result saveVoiceRecord(String filePath) {
//        //先进行参数二次验证，防止validation不生效
//        if (filePath == null || filePath.isEmpty()) {
//            return Result.error("文件路径不能为空");
//        }

        //调用语音识别接口，识别语音文件，接受返回的stgVoiceRecognition对象
        StgVoiceRecognition stgVoiceRecognition = IatUtil.start(filePath);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        stgVoiceRecognition.setCreateTime(currentTime); // 设置当前时间为创建时间
        stgVoiceRecognition.setUpdateTime(currentTime); // 设置当前时间为更新时间
        // 假设creator和updater是固定的或者从上下文中获取
        stgVoiceRecognition.setCreator("admin");
        stgVoiceRecognition.setUpdater("admin");

        int i = dataMapper.insertVoiceRecord(stgVoiceRecognition);
        if (i == 0) {
            return Result.error("新增失败");
        }
        return Result.success("新增成功");
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
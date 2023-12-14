package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 * 搜索请求类，用于接收客户端发送的搜索请求。
 */
@Data
public class SearchRequest {

    // 模型ID，可以为空
    private String modelId;

    // 模型分类列表，可以为空
    private List<String> modelCategory;

    // 样本划分列表，可以为空
    private List<String> sampleDivision;

    // 模型算法列表，可以为空
    private List<String> algorithmName;

    // 触发机制列表，可以为空
    private List<String> triggerMechanism;

    // 创建时间的开始，查询这个时间之后的记录
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime creationTimeStart;
    private String creationTimeStart;

    // 创建时间的结束，查询这个时间之前的记录
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime creationTimeEnd;
    private String creationTimeEnd;

    // 页码
    private Integer pageNumber;

    // 每页大小
    private Integer pageSize;

}

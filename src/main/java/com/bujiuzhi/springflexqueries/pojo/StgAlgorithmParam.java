package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 模型算法实体类，对应数据库中的stg_model_algorithm表。
 */
@Data
public class StgAlgorithmParam {

    private String algorithmId; // 算法ID

    @NotBlank(message = "算法名称不能为空")
    private String algorithmName; // 算法名称

    private String algorithmDesc; // 算法描述

    private String algorithmType; // 算法类型

    private String algorithmParameters; // 算法参数

    private String modelId; // 模型ID

    private String creator; // 创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime creationTime; // 创建时间
    private String creationTime; // 创建时间

    private String updater; // 更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updateTime; // 更新时间
    private String updateTime; // 更新时间

}
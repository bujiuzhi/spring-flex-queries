package com.bujiuzhi.springflexqueries.pojo;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Map;

/**
 * ModelJobManagerInfo 类用于封装与模型作业相关的信息以及其他附加数据。
 * 它通常用于在应用层之间传递模型作业数据和其他必要信息。
 */
@Data
public class ModelJobManagerInfo {
    // ModelJob 对象，包含模型作业的详细信息。
    @Valid
    private ModelJob modelJob;

    // 附加数据的 Map，可能包含诸如操作用户、时间戳等附加信息。
    private Map<String, Object> additionalData;
}
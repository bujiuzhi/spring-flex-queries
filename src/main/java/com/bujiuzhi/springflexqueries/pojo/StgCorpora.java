package com.bujiuzhi.springflexqueries.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * StgCorpora 实体类
 * 对应数据库中的 stg_corpora 表。
 * 此表用于存储语料库信息，包括语料的名称、类型、上传人、上传日期等信息。
 * 主要用于管理和查询存储的语料数据。
 */
@Data
public class StgCorpora {
    @NotBlank(message = "主键ID不能为空", groups = StgCorpora.Update.class)
    private String id; // 主键ID

    @NotBlank(message = "语料名称不能为空", groups = StgCorpora.Insert.class)
    private String name; // 语料名称

    private String type; // 语料类型

    private String annotation; // 语料标注

    private String creator; // 创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime; // 创建时间

    private String updater; // 更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime; // 更新时间

    //public interface Insert extends Default {}
    public interface Insert {
    }

    //如果说某个校验项没有指定分组,默认属于Default分组
    //分组之间可以继承, A extends B  那么A中拥有B中所有的校验项

    //public interface Update extends Default {}
    public interface Update {
    }
}

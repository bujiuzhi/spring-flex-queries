package com.bujiuzhi.springflexqueries.service;

import com.bujiuzhi.springflexqueries.pojo.Result;
import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;

public interface ModelService {

    /**
     * 根据搜索请求进行数据查询。
     *
     * @param searchRequest 包含搜索条件的请求对象
     * @return 返回搜索结果的Result对象。如果搜索成功，Result对象包含数据列表；如果失败，包含错误信息。
     */
    Result search(SearchRequest searchRequest);

    /**
     * 管理模型作业的设置，实现新增或更新逻辑。
     *
     * @param stgModelJob 模型作业对象
     * @return 返回操作结果，封装在Result对象中。
     */
    Result manage(StgModelJob stgModelJob);
}



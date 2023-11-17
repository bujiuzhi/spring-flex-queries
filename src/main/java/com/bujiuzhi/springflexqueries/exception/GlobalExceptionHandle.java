package com.bujiuzhi.springflexqueries.exception;

import com.bujiuzhi.springflexqueries.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

    // 捕获校验异常
    @ExceptionHandler(Exception.class)
    public Result handleExceptions(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }

}

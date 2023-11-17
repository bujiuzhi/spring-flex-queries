package com.bujiuzhi.springflexqueries.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    //操作成功（带响应数据）
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //操作成功（不带响应数据）
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    //操作失败（不带响应数据）
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}

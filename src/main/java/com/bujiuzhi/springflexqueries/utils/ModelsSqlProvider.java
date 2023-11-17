package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.ModelJob;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

// ModelsSqlProvider类负责提供动态生成的SQL语句。
public class ModelsSqlProvider {

    // 动态构建搜索模型作业的SQL语句。
    public String search(ModelJob modelJob) {
        // 使用MyBatis提供的SQL帮助类构建查询语句。
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("test.model_jobs");

        // 反射获取ModelJob类的所有字段，用于构建查询条件。
        Field[] fields = ModelJob.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(modelJob);
                if (value != null) {
                    // 如果字段值不为空，则作为查询条件。
                    String columnName = field.getName();
                    sql.WHERE(columnName + " = #{" + columnName + "}");
                }
            } catch (IllegalAccessException e) {
                // 异常处理，这里只是打印堆栈，实际应用中应该使用日志记录。
                e.printStackTrace();
            }
        }
        // 返回构建好的SQL查询语句。
        return sql.toString();
    }

    // 动态构建插入模型作业的SQL语句。
    public String insert(ModelJob modelJob) {
        SQL sql = new SQL();
        sql.INSERT_INTO("test.model_jobs");

        // 使用反射机制遍历ModelJob对象的所有属性，将其作为插入语句的字段和值。
        Field[] fields = ModelJob.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(modelJob);
                if (value != null) {
                    String columnName = field.getName();
                    sql.VALUES(columnName, "#{" + columnName + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 返回构建好的SQL插入语句。
        return sql.toString();
    }

    // 动态构建更新模型作业的SQL语句。
    public String update(ModelJob modelJob) {
        SQL sql = new SQL();
        sql.UPDATE("test.model_jobs");

        // 反射获取ModelJob类的所有字段，除了model_id作为条件外，其他所有字段都需要更新。
        Field[] fields = ModelJob.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(modelJob);
                if (value != null && !"model_id".equals(field.getName())) {
                    // model_id用作WHERE条件，所以在SET子句中不包含它。
                    String columnName = field.getName();
                    sql.SET(columnName + " = #{" + columnName + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 设置WHERE条件，确保只更新指定的model_id对应的记录。
        sql.WHERE("model_id = #{model_id}");
        // 返回构建好的SQL更新语句。
        return sql.toString();
    }

}
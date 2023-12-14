package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.SearchRequest;
import com.bujiuzhi.springflexqueries.pojo.StgModelJob;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ModelSqlProvider 类用于动态生成SQL语句，配合MyBatis使用。
 */
public class ModelSqlProvider {

    /**
     * 根据搜索请求动态构建SQL查询语句。
     *
     * @param searchRequest 包含搜索条件的对象
     * @param tableName     需要查询的数据库表名
     * @return 构建的SQL查询语句
     */
    public String search(SearchRequest searchRequest, String tableName) {
        SQL sql = new SQL();

        // 获取StgModelJob类的所有字段，创建SELECT语句
        String fieldsWithAliases = generateSelectFieldsWithAliases(StgModelJob.class);
        sql.SELECT(fieldsWithAliases).FROM(tableName);

        // 获取条件
        List<String> whereConditions = generateSqlConditions(searchRequest);
        for (String condition : whereConditions) {
            sql.WHERE(condition);
        }

        // 添加分页逻辑
//        if (searchRequest.getPageNumber() != null && searchRequest.getPageSize() != null) {
//            int offset = (searchRequest.getPageNumber() - 1) * searchRequest.getPageSize();
//            sql.LIMIT(searchRequest.getPageSize()).OFFSET(offset);
//        }
        if (searchRequest.getPageNumber() != null && searchRequest.getPageSize() != null) {
            int offset = (searchRequest.getPageNumber() - 1) * searchRequest.getPageSize();
            String paginationClause = " LIMIT " + searchRequest.getPageSize() + " OFFSET " + offset;
            return sql.toString() + paginationClause;
        }

        return sql.toString();
    }

    /**
     * 根据搜索请求动态构建计算总数据量的 SQL 查询语句。
     *
     * @param searchRequest 包含搜索条件的对象
     * @param tableName     需要查询的数据库表名
     * @return 构建的用于计算总数据量的 SQL 查询语句
     */
    public String count(SearchRequest searchRequest, String tableName) {
        SQL sql = new SQL();

        sql.SELECT("COUNT(*)").FROM(tableName);

        // 使用与 search 相同的方法构建 WHERE 条件
        // 获取条件
        List<String> whereConditions = generateSqlConditions(searchRequest);
        for (String condition : whereConditions) {
            sql.WHERE(condition);
        }

        return sql.toString();
    }

    /**
     * 根据搜索请求动态构建 SQL WHERE 条件。
     *
     * @param searchRequest 包含搜索条件的对象
     * @return 构建的 SQL WHERE 条件列表
     */
    private List<String> generateSqlConditions(SearchRequest searchRequest) {
        List<String> conditions = new ArrayList<>();

        // 通过反射获取SearchRequest中的所有字段
        for (Field field : SearchRequest.class.getDeclaredFields()) {
            field.setAccessible(true); // 确保私有字段也可以访问
            try {
                Object value = field.get(searchRequest);
                String fieldName = field.getName();
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    continue; // 跳过null值或空字符串字段
                }
                String columnName = convertCamelCaseToUnderscore(fieldName);

                // 对于非集合类型的字段，如果不为空，则生成条件语句
                if (!Collection.class.isAssignableFrom(field.getType())) {
                    if ("creationTimeStart".equals(fieldName)) {
                        conditions.add("creation_time >= '" + searchRequest.getCreationTimeStart() + "'");
                    } else if ("creationTimeEnd".equals(fieldName)) {
                        conditions.add("creation_time <= '" + searchRequest.getCreationTimeEnd() + "'");
                    } else if (!"pageNumber".equals(fieldName) && !"pageSize".equals(fieldName)) {
                        // 其他非集合字段直接构建等于条件
                        conditions.add(columnName + " = '" + value.toString().replace("'", "''") + "'");
                    }
                } else {
                    // 处理集合类型字段，使用IN条件
                    Collection<?> collection = (Collection<?>) value;
                    if (!collection.isEmpty()) {
                        String inClause = collection.stream()
                                .map(obj -> "'" + obj.toString().replace("'", "''") + "'")
                                .collect(Collectors.joining(", "));
                        conditions.add(columnName + " IN (" + inClause + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return conditions;
    }

    /**
     * 动态生成查询模型作业的SQL语句。
     * 使用反射生成带有别名的字段列表，确保数据库字段的下划线命名风格
     * 能够正确映射到Java对象的驼峰命名属性上。
     *
     * @param jobId 作业ID，用于查询条件。
     * @return 返回完整的SQL查询语句。
     */
    public String findBy(String jobId) {
        SQL sql = new SQL();
        String fieldsWithAliases = generateSelectFieldsWithAliases(StgModelJob.class);
        sql.SELECT(fieldsWithAliases).FROM("test.stg_model_job").WHERE("job_id = #{jobId}");
        return sql.toString();
    }

    /**
     * 动态生成查询所有“运行中”状态模型作业的SQL语句。
     * 使用反射生成带有别名的字段列表，确保数据库字段的下划线命名风格
     * 能够正确映射到Java对象的驼峰命名属性上。
     *
     * @return 返回完整的SQL查询语句。
     */
    public String findRunningJobs() {
        SQL sql = new SQL();
        String fieldsWithAliases = generateSelectFieldsWithAliases(StgModelJob.class);
        sql.SELECT(fieldsWithAliases).FROM("test.stg_model_job").WHERE("model_status = '运行中'");
        return sql.toString();
    }

    /**
     * 动态构建用于更新操作的SQL语句。
     * 通过反射迭代`StgModelJob`对象的所有属性，将它们添加到SET子句中，
     * 除了用作更新条件的`modelId`和`modelVersion`字段。
     *
     * @param stgModelJob 需要更新的模型作业对象。
     * @param tableName   目标数据库表的名称。
     * @return 构建完成的SQL更新语句。
     */
    public String update(StgModelJob stgModelJob, String tableName) {
        SQL sql = new SQL().UPDATE(tableName);

        // 使用反射获取StgModelJob中的所有字段。
        for (Field field : StgModelJob.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(stgModelJob);
                if (value != null) {
                    String fieldName = field.getName();
                    String columnName = convertCamelCaseToUnderscore(fieldName);
                    // 跳过用作更新条件的主键字段。
                    if (!"jobId".equals(fieldName)) {
                        sql.SET(columnName + " = " + "'" + value.toString().replace("'", "''") + "'");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 设置WHERE条件，通常基于主键或唯一约束进行匹配。
        sql.WHERE("job_id = " + "'" + stgModelJob.getJobId() + "'");

        System.out.println(sql.toString());
        return sql.toString();
    }

    /**
     * 动态构建用于插入操作的SQL语句。
     * 通过反射迭代`StgModelJob`对象的所有非null属性，将它们作为列名和值添加到INSERT语句中。
     *
     * @param stgModelJob 需要插入的模型作业对象。
     * @param tableName   目标数据库表的名称。
     * @return 构建完成的SQL插入语句。
     */
    public String insert(StgModelJob stgModelJob, String tableName) {
        SQL sql = new SQL().INSERT_INTO(tableName);

        // 迭代StgModelJob的属性，构建INSERT语句的列和值。
        for (Field field : StgModelJob.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(stgModelJob);
                if (value != null) {
                    String fieldName = field.getName();
                    String columnName = convertCamelCaseToUnderscore(fieldName);
                    sql.VALUES(columnName, "'" + value.toString().replace("'", "''") + "'");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sql.toString());
        return sql.toString();
    }

    /**
     * 将驼峰命名的字符串转换为下划线命名。
     *
     * @param camelCase 驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    private static String convertCamelCaseToUnderscore(String camelCase) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
    }

    /**
     * 生成带有驼峰命名别名的字段列表的SQL部分。
     *
     * @param modelClass 要为其生成字段列表的模型类。
     * @return 字段列表的SQL字符串。
     */
    public static String generateSelectFieldsWithAliases(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .map(field -> {
                    String columnName = convertCamelCaseToUnderscore(field.getName());
                    return columnName + " as " + field.getName();
                })
                .collect(Collectors.joining(", "));
    }
}

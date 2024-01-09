package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DataSqlProvider 类用于动态生成SQL语句，配合MyBatis使用。
 */
public class DataSqlProvider {


    /**
     * 动态构建SQL查询语句，用于搜索符合条件的语音文件记录。
     *
     * @param params 包含所有搜索条件的Map
     * @return 返回SQL查询语句。
     */
    public String searchVoiceRecords(Map<String, Object> params) {
        SQL sql = new SQL();
        sql.SELECT(generateSelectFieldsWithAliases(StgVoiceRecognition.class)).FROM("test.stg_voice_recognition");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(params);
        whereConditions.forEach(sql::WHERE);

        // 添加分页逻辑
        if (params.containsKey("pageNumber") && params.containsKey("pageSize")) {
            int pageNumber = (Integer) params.get("pageNumber");
            int pageSize = (Integer) params.get("pageSize");
            sql.LIMIT(pageSize).OFFSET((pageNumber - 1) * pageSize);
        }

        return sql.toString();
    }

    /**
     * 动态构建SQL查询语句，用于计算符合条件的语音文件记录总数。
     *
     * @param params 包含所有搜索条件的Map
     * @return 返回SQL查询语句。
     */
    public String countVoiceRecords(Map<String, Object> params) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)").FROM("test.stg_voice_recognition");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(params);
        whereConditions.forEach(sql::WHERE);

        return sql.toString();
    }

    /**
     * 插入语音文件记录
     *
     * @param record
     * @return 返回SQL插入语句。
     */
    public String insertVoiceRecord(StgVoiceRecognition record) {
        return new SQL() {{
            INSERT_INTO("test.stg_voice_recognition");
            for (Field field : StgVoiceRecognition.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(record) != null) {
                        VALUES(convertCamelCaseToUnderscore(field.getName()), "#{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }}.toString();
    }

    /**
     * 根据ID查询语音文件记录
     *
     * @param id
     * @return 返回SQL查询语句。
     */
    public String findVoiceRecordById(final String id) {
        return new SQL() {{
            SELECT(generateSelectFieldsWithAliases(StgVoiceRecognition.class));
            FROM("test.stg_voice_recognition");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 更新语音文件记录
     *
     * @param record
     * @return 返回SQL更新语句。
     */
    public String updateVoiceRecord(StgVoiceRecognition record) {
        return new SQL() {{
            UPDATE("test.stg_voice_recognition");
            for (Field field : StgVoiceRecognition.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(record) != null && !"id".equals(field.getName())) {
                        SET(convertCamelCaseToUnderscore(field.getName()) + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 删除语音文件记录
     *
     * @param id
     * @return 返回SQL删除语句。
     */
    public String deleteVoiceRecord(final String id) {
        return new SQL() {{
            DELETE_FROM("test.stg_voice_recognition");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 动态构建SQL查询语句，用于搜索符合条件的语料库记录。
     *
     * @param params 包含所有搜索条件的Map
     * @return 返回SQL查询语句。
     */
    public String searchCorporaRecords(Map<String, Object> params) {
        SQL sql = new SQL();
        sql.SELECT(generateSelectFieldsWithAliases(StgCorpora.class)).FROM("test.stg_corpora");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(params);
        whereConditions.forEach(sql::WHERE);

        // 添加分页逻辑
        if (params.containsKey("pageNumber") && params.containsKey("pageSize")) {
            int pageNumber = (Integer) params.get("pageNumber");
            int pageSize = (Integer) params.get("pageSize");
            sql.LIMIT(pageSize).OFFSET((pageNumber - 1) * pageSize);
        }

        return sql.toString();
    }

    /**
     * 动态构建SQL查询语句，用于计算符合条件的语料库记录总数。
     *
     * @param params 包含所有搜索条件的Map
     * @return 返回SQL查询语句。
     */
    public String countCorporaRecords(Map<String, Object> params) {
        SQL sql = new SQL();
        sql.SELECT("COUNT(*)").FROM("test.stg_corpora");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(params);
        whereConditions.forEach(sql::WHERE);

        return sql.toString();
    }

    /**
     * 插入语料库记录
     *
     * @param stgCorpora
     * @return 返回SQL插入语句。
     */
    public String insertCorpora(StgCorpora stgCorpora) {
        SQL sql = new SQL();
        sql.INSERT_INTO("test.stg_corpora");
        Arrays.stream(StgCorpora.class.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(stgCorpora);
                if (value != null) {
                    String columnName = convertCamelCaseToUnderscore(field.getName());
                    sql.VALUES(columnName, "#{" + field.getName() + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return sql.toString();
    }

    /**
     * 更新语料库记录
     *
     * @param stgCorpora
     * @return 返回SQL更新语句。
     */
    public String updateCorpora(StgCorpora stgCorpora) {
        SQL sql = new SQL();
        sql.UPDATE("test.stg_corpora");
        Arrays.stream(StgCorpora.class.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            if (!field.getName().equals("id")) { // 跳过ID字段
                try {
                    Object value = field.get(stgCorpora);
                    if (value != null) {
                        String columnName = convertCamelCaseToUnderscore(field.getName());
                        sql.SET(columnName + " = #{" + field.getName() + "}");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    /**
     * 动态构建SQL查询语句，用于搜索符合条件的记录。
     *
     * @param params
     * @return
     */
    private List<String> generateSqlConditions(Map<String, Object> params) {
        List<String> sqlConditions = new ArrayList<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 跳过 pageNumber 和 pageSize
            if ("pageNumber".equals(key) || "pageSize".equals(key)) {
                continue;
            }

            if (value != null && !value.toString().isEmpty()) {
                String columnName;
                String condition;

                // 特殊处理时间列
                if (key.startsWith("start") || key.startsWith("end")) {
                    columnName = getDatabaseFieldNameForKey(key);
                    String operator = key.startsWith("start") ? " >= " : " <= ";
                    condition = columnName + operator + "'" + value + "'";
                } else {
                    // 对于其他类型的字段，使用 LIKE 查询
                    columnName = convertCamelCaseToUnderscore(key);
                    condition = columnName + " LIKE CONCAT('%', '" + value + "', '%')";
                }

                sqlConditions.add(condition);
            }
        }

        return sqlConditions;
    }

    /**
     * 根据参数名获取数据库字段名
     *
     * @param key 参数名
     * @return 数据库字段名
     */
    private String getDatabaseFieldNameForKey(String key) {
        if (key.startsWith("start") || key.startsWith("end")) {
            return convertCamelCaseToUnderscore(key.replace("start", "").replace("end", ""));
        }
        return key; // 默认返回原始键名
    }

    /**
     * 将驼峰命名法转换为下划线命名法
     *
     * @param camelCase
     * @return 返回下划线命名法字符串。
     */
    private static String convertCamelCaseToUnderscore(String camelCase) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
    }

    /**
     * 生成SELECT语句的字段列表，包含别名
     *
     * @param dataClass
     * @return 返回SELECT语句的字段列表。
     */
    private static String generateSelectFieldsWithAliases(Class<?> dataClass) {
        return Arrays.stream(dataClass.getDeclaredFields())
                .map(field -> {
                    String columnName = convertCamelCaseToUnderscore(field.getName());
                    return columnName + " as " + field.getName();
                })
                .collect(Collectors.joining(", "));
    }
}

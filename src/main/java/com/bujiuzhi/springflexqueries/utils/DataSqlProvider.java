package com.bujiuzhi.springflexqueries.utils;

import com.bujiuzhi.springflexqueries.pojo.StgCorpora;
import com.bujiuzhi.springflexqueries.pojo.StgVoiceRecognition;
import org.apache.ibatis.jdbc.SQL;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DataSqlProvider 类用于动态生成SQL语句，配合MyBatis使用。
 */
public class DataSqlProvider {

    /**
     * 动态构建SQL查询语句，用于搜索符合条件的语音文件记录。
     *
     * @param startDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @return 返回SQL查询语句。
     */
    public String searchVoiceRecords(String startDate, String endDate, int pageNumber, int pageSize) {
        // 构建和封装搜索条件Map
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("startDate", startDate);
        conditions.put("endDate", endDate);

        SQL sql = new SQL();
        sql.SELECT(generateSelectFieldsWithAliases(StgVoiceRecognition.class)).FROM("test.stg_voice_recognition");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(conditions);
        whereConditions.forEach(sql::WHERE);

        String finalSql = sql.toString();
        // 添加分页逻辑
        finalSql += " LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;
        return finalSql;
    }

    /**
     * 动态构建SQL查询语句，用于计算符合条件的语音文件记录总数。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 返回SQL查询语句。
     */
    public String countVoiceRecords(String startDate, String endDate) {
        // 构建和封装搜索条件Map
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("startDate", startDate);
        conditions.put("endDate", endDate);

        SQL sql = new SQL();
        sql.SELECT("COUNT(*)").FROM("test.stg_voice_recognition");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(conditions);
        whereConditions.forEach(sql::WHERE);

        return sql.toString();
    }

    /**
     * 插入语音文件记录
     *
     * @param stgVoiceRecognition
     * @return 返回SQL插入语句。
     */
    public String insertVoiceRecord(StgVoiceRecognition stgVoiceRecognition) {
        SQL sql = new SQL();
        sql.INSERT_INTO("test.stg_voice_recognition");
        Arrays.stream(StgVoiceRecognition.class.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(stgVoiceRecognition);
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
     * 动态构建SQL查询语句，用于搜索符合条件的语料库记录。
     *
     * @param startDate
     * @param endDate
     * @param creator
     * @param pageNumber
     * @param pageSize
     * @return 返回SQL查询语句。
     */
    public String searchCorporaRecords(String startDate, String endDate, String creator, int pageNumber, int pageSize) {
        // 构建和封装搜索条件Map
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("startDate", startDate);
        conditions.put("endDate", endDate);
        conditions.put("creator", creator);

        SQL sql = new SQL();
        sql.SELECT(generateSelectFieldsWithAliases(StgCorpora.class)).FROM("test.stg_corpora");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(conditions);
        whereConditions.forEach(sql::WHERE);

        String finalSql = sql.toString();
        // 添加分页逻辑
        finalSql += " LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;
        return finalSql;
    }

    /**
     * 动态构建SQL查询语句，用于计算符合条件的语料库记录总数。
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param creator   上传人
     * @return 返回SQL查询语句。
     */
    public String countCorporaRecords(String startDate, String endDate, String creator) {
        // 构建和封装搜索条件Map
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("startDate", startDate);
        conditions.put("endDate", endDate);
        conditions.put("creator", creator);

        SQL sql = new SQL();
        sql.SELECT("COUNT(*)").FROM("test.stg_corpora");

        // 动态生成WHERE子句
        List<String> whereConditions = generateSqlConditions(conditions);
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
     * 动态生成SQL查询语句的辅助方法
     *
     * @param conditions
     * @return 返回SQL查询语句。
     */
    private List<String> generateSqlConditions(Map<String, Object> conditions) {
        List<String> sqlConditions = new ArrayList<>();

        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null && !value.toString().isEmpty()) {
                String columnName = convertCamelCaseToUnderscore(key);

                // 特殊处理startDate和endDate条件，它们都与createTime字段比较
                if ("startDate".equals(key) || "endDate".equals(key)) {
                    String operator = "startDate".equals(key) ? " >= '" : " <= '";
                    sqlConditions.add("create_time" + operator + value.toString().replace("'", "''") + "'");
                } else {
                    sqlConditions.add(columnName + " = '" + value.toString().replace("'", "''") + "'");
                }
            }
        }

        return sqlConditions;
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

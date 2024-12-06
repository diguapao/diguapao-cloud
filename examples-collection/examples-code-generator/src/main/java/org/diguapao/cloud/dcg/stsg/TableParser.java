package org.diguapao.cloud.dcg.stsg;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析MySQL建表语句并提取表结构信息
 *
 * @author DiGuaPao
 * @version 2024.11.06
 * @since 2024-11-06 15:29:16
 */
@Slf4j
public class TableParser {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TableInfo {
        private String tableName;
        private String tableComment;
        private String className;
        private String primaryKeyType;
        private List<TableParser.Column> columns;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        private String columnName;
        private String name;
        private String type;
        private String comment;
        private boolean isPrimaryKey;

        public Column(String columnName, String name, String type, String comment) {
            this.columnName = columnName;
            this.name = name;
            this.type = type;
            this.comment = comment;
        }
    }

    /**
     * 解析建表语句
     *
     * @param createTableSql 建表语句
     * @return 表字段信息集
     */
    public static List<Column> parseCreateTableStatement(String createTableSql) {
        List<Column> columns = new ArrayList<>();
        boolean foundPrimaryKey = false;
        String primaryKeyColumn = null;

        // 匹配列定义
        Pattern columnPattern = Pattern.compile("`([a-zA-Z0-9_]+)`\\s+([a-zA-Z0-9_()]+)\\s*(?:primary\\s+key)?", Pattern.CASE_INSENSITIVE);
        Matcher columnMatcher = columnPattern.matcher(createTableSql);

        while (columnMatcher.find()) {
            Column column = new Column();
            column.setName(columnMatcher.group(1));
            column.setType(columnMatcher.group(2));
            if (columnMatcher.group().matches("(?i).*primary\\s+key.*")) {
                column.setPrimaryKey(true);
                foundPrimaryKey = true;
                primaryKeyColumn = column.getName();
            } else {
                column.setPrimaryKey(false);
            }
            columns.add(column);
        }

        // 匹配单独定义的 PRIMARY KEY
        if (!foundPrimaryKey) {
            Pattern primaryKeyPattern = Pattern.compile("(?i)primary\\s+key\\s*\\(\\s*`?([a-zA-Z0-9_]+)`?\\s*\\)");
            Matcher primaryKeyMatcher = primaryKeyPattern.matcher(createTableSql);
            if (primaryKeyMatcher.find()) {
                primaryKeyColumn = primaryKeyMatcher.group(1);
                for (Column column : columns) {
                    if (column.getName().equals(primaryKeyColumn)) {
                        column.setPrimaryKey(true);
                        break;
                    }
                }
            }
        }

        return columns;
    }


    /**
     * 提取表名
     *
     * @param createTableSql 建表语句
     * @return 表名
     */
    public static String extractTableName(String createTableSql) {
        Pattern tablePattern = Pattern.compile("(?i)create\\s+table\\s+if\\s+not\\s+exists\\s+`?([a-zA-Z0-9_]+)`?");
        Matcher matcher = tablePattern.matcher(createTableSql);
        if (matcher.find()) {
            return matcher.group(1);
        }

        // 如果没有找到 IF NOT EXISTS，尝试匹配普通的 CREATE TABLE
        tablePattern = Pattern.compile("(?i)create\\s+table\\s+`?([a-zA-Z0-9_]+)`?");
        matcher = tablePattern.matcher(createTableSql);
        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new IllegalArgumentException("无法从 SQL 语句中提取表名");
    }

    /**
     * 解析SQL文件
     *
     * @param filePath    文件路径
     * @param tablePrefix
     * @return SQL文件解析出来的表信息集
     * @throws IOException
     */
    @SneakyThrows
    public static List<TableInfo> parseSqlFile(String filePath, String tablePrefix) throws IOException {
        List<TableInfo> tableInfos = new ArrayList<>();
        StringBuilder createTableSql = new StringBuilder();
        boolean isCreatingTable = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.toUpperCase().startsWith("CREATE TABLE")) {
                    isCreatingTable = true;
                    // 清空 StringBuilder
                    createTableSql.setLength(0);
                    createTableSql.append(line);
                } else if (isCreatingTable) {
                    createTableSql.append(" ").append(line);
                    if (line.endsWith(";")) {
                        isCreatingTable = false;
                        String finalCreateTableSql = createTableSql.toString();
                        AtomicReference<String> primaryKeyType = new AtomicReference<>("Long");

                        // 解析建表语句
                        //List<Column> columns = parseCreateTableStatement(finalCreateTableSql);
                        CreateTable createTable = (CreateTable) CCJSqlParserUtil.parse(finalCreateTableSql);
                        log.info("Table Name: " + createTable.getTable().getName());

                        // 构建表字段
                        List<Column> columns = createTable.getColumnDefinitions().stream()
                                .map(cd -> {
                                    String fieldName = StrUtil.lowerFirst(toJavaClassName(cd.getColumnName())).replace("`", StrUtil.EMPTY);
                                    String fieldType = cd.getColDataType().getDataType();
                                    String javaType = DbTypeToJavaTypeConverter.convert(fieldType);

                                    String comment = fieldName;
                                    List<String> columnSpecs = cd.getColumnSpecs();
                                    if (CollectionUtil.isNotEmpty(columnSpecs)) {
                                        if (containsChineseChars(comment = columnSpecs.get(columnSpecs.size() - 1)) && !"null".equals(comment)) {
                                            comment = comment.replace("'", StrUtil.EMPTY).replace("\"", StrUtil.EMPTY);
                                        } else {
                                            comment = "";
                                        }
                                        //设置主键类型
                                        if (columnSpecs.contains("primary")) {
                                            primaryKeyType.set(javaType);
                                        }
                                    }

                                    return new Column(cd.getColumnName(), fieldName, javaType, comment);
                                })
                                .collect(Collectors.toList());

                        TableInfo tableInfo = new TableInfo();
                        /*extractTableName(finalCreateTableSql)*/
                        String tableName = createTable.getTable().getName().replace(tablePrefix, StrUtil.EMPTY);
                        String className = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
                        tableInfo.setTableName(tableName);
                        String tableComment = createTable.getTableOptionsStrings().get(createTable.getTableOptionsStrings().size() - 1)
                                .replace("'", StrUtil.EMPTY).replace("\"", StrUtil.EMPTY);
                        tableInfo.setTableComment(tableComment);
                        tableInfo.setClassName(toJavaClassName(className));
                        tableInfo.setColumns(columns);
                        tableInfo.setPrimaryKeyType(primaryKeyType.get());

                        tableInfos.add(tableInfo);
                    }
                }
            }
        }

        return tableInfos;
    }

    /**
     * 读取SQL文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readSqlFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * 表名转类名
     *
     * @param tableName 表名
     * @return 类名
     */
    public static String toJavaClassName(String tableName) {
        // 分割表名中的下划线
        String[] parts = tableName.split("_");
        StringBuilder javaClassName = new StringBuilder();

        for (String part : parts) {
            // 将每个部分的首字母大写
            if (!part.isEmpty()) {
                javaClassName.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    javaClassName.append(part.substring(1).toLowerCase());
                }
            }
        }

        return javaClassName.toString();
    }

    public static boolean containsChineseChars(String str) {
        // 使用正则表达式检查字符串是否包含中文字符
        return str.matches(".*[\\u4e00-\\u9fa5].*");
    }

}
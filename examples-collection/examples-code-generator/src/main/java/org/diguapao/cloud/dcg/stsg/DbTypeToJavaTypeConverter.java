package org.diguapao.cloud.dcg.stsg;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型转JAVA类型
 *
 * @author DiGuaPao
 * @version 2024.11.06
 * @since 2024-11-06 15:29:16
 */
public class DbTypeToJavaTypeConverter {
    private static final Map<String, String> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put("int", "Integer");
        TYPE_MAP.put("bigint", "Long");
        TYPE_MAP.put("smallint", "Short");
        //TYPE_MAP.put("tinyint", "Byte");
        TYPE_MAP.put("tinyint", "Integer");
        TYPE_MAP.put("decimal", "BigDecimal");
        TYPE_MAP.put("double", "Double");
        TYPE_MAP.put("float", "Float");
        TYPE_MAP.put("char", "String");
        TYPE_MAP.put("varchar", "String");
        TYPE_MAP.put("text", "String");
        TYPE_MAP.put("mediumtext", "String");
        TYPE_MAP.put("longtext", "String");
        TYPE_MAP.put("date", "LocalDate");
        TYPE_MAP.put("datetime", "LocalDateTime");
        TYPE_MAP.put("timestamp", "LocalDateTime");
        TYPE_MAP.put("time", "LocalTime");
        TYPE_MAP.put("boolean", "Boolean");
        TYPE_MAP.put("bit", "Boolean");
        // TINYBLOB 最大长度为 255 字节（约 0.000244 MB）
        TYPE_MAP.put("tinyblob", "byte[]");
        // BLOB 最大长度为 65,535 字节（约 0.0625 MB）
        TYPE_MAP.put("blob", "byte[]");
        // MEDIUMBLOB 最大长度为 16,777,215 字节（约 16 MB）
        TYPE_MAP.put("mediumblob", "byte[]");
        // LONGBLOB 最大长度为 4,294,967,295 字节（约 4 GB）
        TYPE_MAP.put("longblob", "byte[]");
    }

    public static String convert(String dbType) {
        String javaType = TYPE_MAP.get(dbType.toLowerCase());
        if (javaType == null) {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
        return javaType;
    }
}
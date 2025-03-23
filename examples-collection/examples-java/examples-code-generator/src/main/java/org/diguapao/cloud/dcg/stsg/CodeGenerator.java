package org.diguapao.cloud.dcg.stsg;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * 生成代码
 *
 * @author DiGuaPao
 * @version 2024.11.06
 * @since 2024-11-06 15:29:16
 */
@Slf4j
@Data
@SuppressWarnings("ALL")
public class CodeGenerator {

    private String tablePrefix;

    private static final String TEMPLATE_DIR = getUserDir() + "examples-collection/examples-java/examples-code-generator/src/main/resources/templates/stsg/";
    private static final String OUTPUT_DIR = getUserDir() + "examples-collection/examples-java/examples-code-generator/src/main/java/";

    public static void main(String[] args) throws IOException {
        try {
            CodeGenerator cg = new CodeGenerator("car_");
            //建表语句SQL文件
            String sqlFilePath = getUserDir() + "examples-collection/examples-java/examples-code-generator/src/main/java/org/diguapao/cloud/dcg/stsg/TapeGenSql.sql";
            List<TableParser.TableInfo> tableInfos = TableParser.parseSqlFile(sqlFilePath, cg.getTablePrefix());

            for (int i = 0; i < tableInfos.size(); i++) {
                TableParser.TableInfo tableInfo = tableInfos.get(i);
                String tableName = tableInfo.getTableName();
                String className = tableInfo.getClassName();
                String tableComment = tableInfo.getTableComment();
                String primaryKeyType = tableInfo.getPrimaryKeyType();
                List<TableParser.Column> columns = tableInfo.getColumns();
                log.info("tableName={}，className={}，tableComment={}", tableName, className, tableComment);

                // 打印已解析的列
                //for (TableParser.Column column : columns) {
                //    log.info("Column Name: {}", column.getName());
                //    log.info("Column Type: {}", column.getType());
                //    log.info("Is Primary Key: {}", column.isPrimaryKey());
                //}

                // 业务域关键词、基础包名、common 包名、config 包名
                String domainKey = "car", packagePrefix = "com.csair.sc", commonPackagePrefix = packagePrefix + ".common.core", configPackagePrefix = packagePrefix + ".config";

                if (i == 0) {
                    cg.generateFile("CommonConstants.java.vm", commonPackagePrefix, commonPackagePrefix, tableName, "CommonConstants", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + commonPackagePrefix.replace(".", "/") + "/constant/" + "CommonConstants" + ".java");
                    cg.generateFile("R.java.vm", commonPackagePrefix, commonPackagePrefix, tableName, "R", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + commonPackagePrefix.replace(".", "/") + "/util/" + "R" + ".java");
                    cg.generateFile("PageResult.java.vm", commonPackagePrefix, commonPackagePrefix, tableName, "PageResult", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + commonPackagePrefix.replace(".", "/") + "/vo/" + "PageResult" + ".java");
                    cg.generateFile("PageParam.java.vm", commonPackagePrefix, commonPackagePrefix, tableName, "PageParam", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + commonPackagePrefix.replace(".", "/") + "/vo/" + "PageParam" + ".java");


                    cg.generateFile("JacksonConfig.java.vm", configPackagePrefix, configPackagePrefix, tableName, "JacksonConfig", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + configPackagePrefix.replace(".", "/") + "/" + "JacksonConfig" + ".java");
                    cg.generateFile("MyBatisPlusConfig.java.vm", configPackagePrefix, configPackagePrefix, tableName, "MyBatisPlusConfig", tableComment, primaryKeyType, columns,
                            OUTPUT_DIR + configPackagePrefix.replace(".", "/") + "/" + "MyBatisPlusConfig" + ".java");
                }

                packagePrefix = packagePrefix + "." + domainKey + "." + StrUtil.lowerFirst(tableInfo.getClassName());
                cg.generateFile("PO.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/entity/" + className + ".java");
                cg.generateFile("VO.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/vo/" + className + "VO.java");
                cg.generateFile("DTO.java.vm", packagePrefix + ".api", commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + (packagePrefix + ".api").replace(".", "/") + "/dto/" + className + "DTO.java");
                cg.generateFile("QueryDTO.java.vm", packagePrefix + ".api", commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + (packagePrefix + ".api").replace(".", "/") + "/dto/" + className + "QueryDTO.java");

                cg.generateFile("Mapper.xml.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/mapper/" + className + "Mapper.xml");
                cg.generateFile("Mapper.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/mapper/" + className + "Mapper.java");

                cg.generateFile("Service.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/service/" + className + "Service.java");
                cg.generateFile("ServiceImpl.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/service/impl/" + className + "ServiceImpl.java");

                cg.generateFile("Controller.java.vm", packagePrefix, commonPackagePrefix, tableName, className, tableComment, primaryKeyType, columns,
                        OUTPUT_DIR + packagePrefix.replace(".", "/") + "/controller/" + className + "Controller.java");
            }

        } catch (IOException | IllegalArgumentException e) {
            log.error("", e);
        }
    }

    /**
     * @param templateName
     * @param packagePrefix
     * @param commonPackagePrefix 某些类里需要 packagePrefix、commonPackagePrefix
     * @param tableName
     * @param className
     * @param tableComment
     * @param primaryKeyType
     * @param columns
     * @param outputFilePath
     * @throws IOException
     */
    private void generateFile(String templateName, String packagePrefix, String commonPackagePrefix, String tableName, String className, String tableComment, String primaryKeyType,
                              List<TableParser.Column> columns, String outputFilePath) throws IOException {
        VelocityEngine ve = new VelocityEngine();
        Properties props = new Properties();
        props.setProperty("resource.loader", "file");
        props.setProperty("file.resource.loader.path", TEMPLATE_DIR);
        ve.init(props);

        Template template = ve.getTemplate(templateName);
        VelocityContext context = new VelocityContext();
        context.put("packagePrefix", packagePrefix);
        context.put("commonPackagePrefix", commonPackagePrefix);
        context.put("tableName", tableName);
        context.put("tableComment", tableComment);
        context.put("className", className);
        context.put("lfClassName", StrUtil.lowerFirst(className));
        context.put("columns", columns);
        context.put("author", "LiPiao");
        context.put("version", "1.0");
        context.put("since", DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
        context.put("primaryKeyType", primaryKeyType);

        File outputFile = new File(outputFilePath);
        Files.createDirectories(Paths.get(outputFile.getParent()));
        try (Writer writer = new FileWriter(outputFile)) {
            template.merge(context, writer);
        }
    }

    private static String getUserDir() {
        return System.getProperty("user.dir").replace("\\", "/") + "/";
    }

    public CodeGenerator(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
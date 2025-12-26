package org.diguapao.cloud.framework.rocketmq.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * SQLite数据库初始化工具 (JDK 8兼容版本)
 * <p>
 * FIXME 当然，切换到 H2 后，这个工具类基本不再需要！
 *
 * @author diguapao
 */
@Slf4j
public class SQLiteDatabaseUtil {

    /**
     * 初始化SQLite数据库
     *
     * @param dbPath 数据库文件路径
     */
    public static void initDatabase(String dbPath) {
        Connection connection = null;
        Statement statement = null;
        try {
            // 注册SQLite驱动
            Class.forName("org.sqlite.JDBC");

            // 设置SQLite连接属性
            Properties props = new Properties();
            props.setProperty("date_string_format", "yyyy-MM-dd HH:mm:ss");

            // 创建数据库连接
            String url = "jdbc:sqlite:" + dbPath;
            connection = DriverManager.getConnection(url, props);

            log.info("SQLite数据库连接成功: {}", dbPath);

            // 启用外键约束
            statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON");
            log.info("SQLite外键约束已启用");

            // 创建表
            createTables(connection);

            // 插入测试数据（可选）
            // insertTestData(connection);

        } catch (Exception e) {
            log.error("初始化SQLite数据库失败", e);
        } finally {
            // 关闭资源
            closeResources(statement, connection);
        }
    }

    /**
     * 创建数据库表
     */
    private static void createTables(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();

            // 订单表
            String createOrderTable = "CREATE TABLE IF NOT EXISTS t_order (" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "order_no VARCHAR(32) UNIQUE NOT NULL, " +
                    "status VARCHAR(20) NOT NULL, " +
                    "user_id BIGINT NOT NULL, " +
                    "user_name VARCHAR(100), " +
                    "user_phone VARCHAR(11), " +
                    "merchant_id BIGINT, " +
                    "merchant_name VARCHAR(100), " +
                    "total_quantity INTEGER DEFAULT 0, " +
                    "original_amount DECIMAL(10,2) DEFAULT 0, " +
                    "discount_amount DECIMAL(10,2) DEFAULT 0, " +
                    "shipping_fee DECIMAL(10,2) DEFAULT 0, " +
                    "pay_amount DECIMAL(10,2) NOT NULL, " +
                    "pay_method INTEGER, " +
                    "transaction_id VARCHAR(100), " +
                    "pay_time DATETIME, " +
                    "pay_expire_time DATETIME, " +
                    "receiver_name VARCHAR(100), " +
                    "receiver_phone VARCHAR(11), " +
                    "receiver_address VARCHAR(500), " +
                    "logistics_company VARCHAR(100), " +
                    "tracking_no VARCHAR(100), " +
                    "ship_time DATETIME, " +
                    "actual_delivery_time DATETIME, " +
                    "create_time DATETIME DEFAULT (datetime('now', 'localtime')), " +
                    "update_time DATETIME DEFAULT (datetime('now', 'localtime')), " +
                    "complete_time DATETIME, " +
                    "cancel_time DATETIME, " +
                    "cancel_reason VARCHAR(200), " +
                    "user_remark VARCHAR(1000), " +
                    "ext_data TEXT DEFAULT '{}', " +
                    "version INTEGER DEFAULT 0, " +
                    "deleted INTEGER DEFAULT 0" +
                    ")";

            // 订单商品表
            String createOrderItemTable = "CREATE TABLE IF NOT EXISTS t_order_item (" +
                    "item_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "order_id BIGINT NOT NULL, " +
                    "order_no VARCHAR(32) UNIQUE NOT NULL, " +
                    "product_id BIGINT NOT NULL, " +
                    "sku_id BIGINT, " +
                    "product_name VARCHAR(200) NOT NULL, " +
                    "product_image VARCHAR(500), " +
                    "specifications VARCHAR(500), " +
                    "unit_price DECIMAL(10,2) DEFAULT 0, " +
                    "quantity INTEGER DEFAULT 1, " +
                    "total_price DECIMAL(10,2) DEFAULT 0, " +
                    "discount_amount DECIMAL(10,2) DEFAULT 0, " +
                    "actual_amount DECIMAL(10,2) DEFAULT 0, " +
                    "commented BOOLEAN DEFAULT 0, " +
                    "deleted INTEGER DEFAULT 0," +
                    "FOREIGN KEY (order_id) REFERENCES t_order(order_id) ON DELETE CASCADE" +
                    ")";

            // 订单日志表
            String createOrderLogTable = "CREATE TABLE IF NOT EXISTS t_order_log (" +
                    "log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "order_id BIGINT NOT NULL, " +
                    "order_no VARCHAR(32) UNIQUE NOT NULL, " +
                    "operation_type VARCHAR(50) NOT NULL, " +
                    "from_status VARCHAR(20), " +
                    "to_status VARCHAR(20), " +
                    "operation_content VARCHAR(1000), " +
                    "operator_type VARCHAR(20) NOT NULL, " +
                    "operator_id BIGINT, " +
                    "operator_name VARCHAR(100), " +
                    "ip_address VARCHAR(50), " +
                    "operation_time DATETIME DEFAULT (datetime('now', 'localtime')), " +
                    "operation_detail TEXT DEFAULT '{}', " +
                    "FOREIGN KEY (order_id) REFERENCES t_order(order_id) ON DELETE CASCADE" +
                    ")";

            // 执行建表语句
            statement.execute(createOrderTable);
            log.info("创建订单表成功");

            statement.execute(createOrderItemTable);
            log.info("创建订单商品表成功");

            statement.execute(createOrderLogTable);
            log.info("创建订单日志表成功");

            // 创建索引
            createIndexes(statement);

        } finally {
            closeResources(statement, null);
        }
    }

    /**
     * 创建索引
     */
    private static void createIndexes(Statement statement) throws SQLException {
        try {
            String[] indexSqls = {
                    "CREATE INDEX IF NOT EXISTS idx_order_user_status ON t_order(user_id, status)",
                    "CREATE INDEX IF NOT EXISTS idx_order_no ON t_order(order_no)",
                    "CREATE INDEX IF NOT EXISTS idx_order_create_time ON t_order(create_time)",
                    "CREATE INDEX IF NOT EXISTS idx_order_pay_time ON t_order(pay_time)",
                    "CREATE INDEX IF NOT EXISTS idx_order_cancel_time ON t_order(cancel_time)",
                    "CREATE INDEX IF NOT EXISTS idx_order_log_order_id ON t_order_log(order_id)",
                    "CREATE INDEX IF NOT EXISTS idx_order_log_operation_time ON t_order_log(operation_time)",
                    "CREATE INDEX IF NOT EXISTS idx_order_item_order_id ON t_order_item(order_id)",
                    "CREATE INDEX IF NOT EXISTS idx_order_item_product_id ON t_order_item(product_id)"
            };

            for (String sql : indexSqls) {
                statement.execute(sql);
            }

            log.info("数据库索引创建成功");

        } catch (SQLException e) {
            log.error("创建数据库索引失败", e);
            throw e;
        }
    }

    /**
     * 插入测试数据（可选）
     */
    private static void insertTestData(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();

            // 检查是否已有测试数据
            String checkSql = "SELECT COUNT(*) FROM t_order";
            int count = statement.executeQuery(checkSql).getInt(1);

            if (count == 0) {
                // 插入测试订单
                String insertOrder = "INSERT INTO t_order (" +
                        "order_no, status, user_id, user_name, user_phone, " +
                        "total_quantity, original_amount, discount_amount, shipping_fee, pay_amount, " +
                        "receiver_name, receiver_phone, receiver_address, user_remark" +
                        ") VALUES (" +
                        "'TEST001', 'PENDING_PAY', 1001, '测试用户', '13800138000', " +
                        "3, 588.00, 50.00, 15.00, 553.00, " +
                        "'张三', '13800138000', '北京市朝阳区渔具大街888号', '测试订单'" +
                        ")";

                statement.execute(insertOrder);

                // 获取插入的订单ID
                String getOrderIdSql = "SELECT last_insert_rowid()";
                long orderId = statement.executeQuery(getOrderIdSql).getLong(1);

                // 插入测试商品
                String[] insertItems = {
                        "INSERT INTO t_order_item (order_id, product_id, product_name, specifications, " +
                                "unit_price, quantity, total_price, discount_amount, actual_amount) VALUES (" +
                                orderId + ", 101, '黑坑战斗竿 4.5米', '硬度:8H,长度:4.5米', " +
                                "299.00, 1, 299.00, 20.00, 279.00)",

                        "INSERT INTO t_order_item (order_id, product_id, product_name, specifications, " +
                                "unit_price, quantity, total_price, discount_amount, actual_amount) VALUES (" +
                                orderId + ", 102, '不锈钢竞技鱼护 40cm', '材质:不锈钢,直径:40cm', " +
                                "149.00, 1, 149.00, 15.00, 134.00)",

                        "INSERT INTO t_order_item (order_id, product_id, product_name, specifications, " +
                                "unit_price, quantity, total_price, discount_amount, actual_amount) VALUES (" +
                                orderId + ", 103, '野战蓝鲫饵料 500g', '味型:腥香,净重:500g', " +
                                "28.00, 5, 140.00, 15.00, 125.00)"
                };

                for (String sql : insertItems) {
                    statement.execute(sql);
                }

                // 插入测试日志
                String insertLog = "INSERT INTO t_order_log (" +
                        "order_id, operation_type, from_status, to_status, " +
                        "operation_content, operator_type, operator_id, operator_name) VALUES (" +
                        orderId + ", 'CREATE', NULL, 'PENDING_PAY', " +
                        "'用户创建测试订单', 'USER', 1001, '测试用户')";

                statement.execute(insertLog);

                log.info("测试数据插入成功，订单ID: {}", orderId);
            }

        } catch (SQLException e) {
            log.error("插入测试数据失败", e);
        } finally {
            closeResources(statement, null);
        }
    }

    /**
     * 执行SQL脚本文件
     *
     * @param connection 数据库连接
     * @param scriptPath SQL脚本文件路径（classpath相对路径）
     */
    public static void executeSqlScript(Connection connection, String scriptPath) {
        Statement statement = null;
        BufferedReader reader = null;
        try {
            // 读取SQL脚本文件
            ClassPathResource resource = new ClassPathResource(scriptPath);
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过注释和空行
                if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }
                sqlBuilder.append(line);
                if (line.trim().endsWith(";")) {
                    String sql = sqlBuilder.toString().trim();
                    if (!sql.isEmpty()) {
                        statement = connection.createStatement();
                        statement.execute(sql.substring(0, sql.length() - 1)); // 移除末尾的分号
                        statement.close();
                    }
                    sqlBuilder.setLength(0);
                }
            }

            log.info("SQL脚本执行完成: {}", scriptPath);

        } catch (Exception e) {
            log.error("执行SQL脚本失败: {}", scriptPath, e);
        } finally {
            closeResources(statement, null);
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    log.error("关闭SQL脚本读取器失败", e);
                }
            }
        }
    }

    /**
     * 关闭数据库资源
     */
    private static void closeResources(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("关闭Statement失败", e);
            }
        }

        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("关闭Connection失败", e);
            }
        }
    }

    /**
     * 备份数据库
     *
     * @param sourceDbPath 源数据库路径
     * @param backupDbPath 备份数据库路径
     */
    public static void backupDatabase(String sourceDbPath, String backupDbPath) {
        Connection sourceConn = null;
        Connection backupConn = null;
        Statement statement = null;

        try {
            // 连接源数据库
            String sourceUrl = "jdbc:sqlite:" + sourceDbPath;
            sourceConn = DriverManager.getConnection(sourceUrl);

            // 连接备份数据库
            String backupUrl = "jdbc:sqlite:" + backupDbPath;
            backupConn = DriverManager.getConnection(backupUrl);

            // 获取源数据库的所有表
            statement = sourceConn.createStatement();
            var tables = statement.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'"
            );

            // 备份每个表
            while (tables.next()) {
                String tableName = tables.getString("name");
                backupTable(sourceConn, backupConn, tableName);
            }

            log.info("数据库备份完成: {} -> {}", sourceDbPath, backupDbPath);

        } catch (Exception e) {
            log.error("数据库备份失败", e);
        } finally {
            closeResources(statement, sourceConn);
            closeResources(null, backupConn);
        }
    }

    /**
     * 备份单个表
     */
    private static void backupTable(Connection sourceConn, Connection backupConn, String tableName)
            throws SQLException {
        Statement sourceStmt = null;
        Statement backupStmt = null;

        try {
            // 获取表结构
            sourceStmt = sourceConn.createStatement();
            var createTableRs = sourceStmt.executeQuery(
                    String.format("SELECT sql FROM sqlite_master WHERE type='table' AND name='%s'", tableName)
            );

            if (createTableRs.next()) {
                String createTableSql = createTableRs.getString("sql");

                // 在备份数据库创建表
                backupStmt = backupConn.createStatement();
                backupStmt.execute(createTableSql);
                backupStmt.close();

                // 复制数据
                var dataRs = sourceStmt.executeQuery("SELECT * FROM " + tableName);
                var metaData = dataRs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (dataRs.next()) {
                    StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = dataRs.getObject(i);
                        if (value instanceof String) {
                            insertSql.append("'").append(value.toString().replace("'", "''")).append("'");
                        } else if (value == null) {
                            insertSql.append("NULL");
                        } else {
                            insertSql.append(value);
                        }
                        if (i < columnCount) {
                            insertSql.append(", ");
                        }
                    }
                    insertSql.append(")");

                    backupStmt = backupConn.createStatement();
                    backupStmt.execute(insertSql.toString());
                    backupStmt.close();
                }

                log.info("表备份完成: {}", tableName);
            }

        } finally {
            closeResources(sourceStmt, null);
            closeResources(backupStmt, null);
        }
    }
}
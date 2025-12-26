package org.diguapao.cloud.framework.rocketmq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.diguapao.cloud.framework.rocketmq.utils.SQLiteDatabaseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据库配置类
 * @author diguapao
 */
@Slf4j
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 创建Druid数据源
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        // 基本配置
        dataSource.setUrl(dbUrl);
        dataSource.setDriverClassName(driverClassName);

        // 连接池配置
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);

        // 检测配置
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        // 开启缓存
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        // 配置监控
        try {
            dataSource.setFilters("stat,wall,slf4j");
        } catch (SQLException e) {
            log.error("Druid配置监控过滤器失败", e);
        }

        return dataSource;
    }

    /**
     * 初始化数据库
     */
    @PostConstruct
    public void initDatabase() {
        try {
            // 从URL中提取数据库文件路径
            String dbPath = dbUrl.replace("jdbc:sqlite:", "");
            log.info("初始化SQLite数据库: {}", dbPath);

            // 初始化数据库和表
            SQLiteDatabaseUtil.initDatabase(dbPath);

        } catch (Exception e) {
            log.error("初始化数据库失败", e);
        }
    }

    /**
     * MyBatis Plus 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        return interceptor;
    }

}
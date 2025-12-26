package org.diguapao.cloud.framework.rocketmq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * H2 数据库配置类（替代原 SQLite 配置）
 *
 * @author diguapao
 */
@Slf4j
@Configuration
public class DatabaseH2Config {

    /**
     * 创建 Druid 数据源（自动从 application.yml 读取配置）
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        // DruidDataSource 会自动读取 spring.datasource.url / username / password 等
        return new DruidDataSource();
    }

    /**
     * MyBatis-Plus 分页插件（H2 兼容）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}
package org.diguapao.cloud.framework.rocketmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author diguapao
 * @version 2025.0.1
 * @since 2025/12/19 11:15
 */
@MapperScan("org.diguapao.cloud.framework.rocketmq.core.mapper")
@SpringBootApplication
public class RocketMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQApplication.class, args);
    }
}

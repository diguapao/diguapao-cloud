package org.diguapao.cloud.framework.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author diguapao
 * @version 2025.0.1
 * @since 2025/12/19 11:15
 */
@SpringBootApplication
public class RocketMQApplication {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RocketMQApplication.class, args);
    }

}

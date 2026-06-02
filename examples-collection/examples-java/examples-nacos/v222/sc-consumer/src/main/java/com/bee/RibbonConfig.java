package com.bee;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {

    @Bean
    // 🌟 激活 Ribbon 负载均衡核心注解
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 选填：配置 Ribbon 的负载均衡策略
     * 默认是轮询（RoundRobinRule），如果想改成随机（RandomRule）或者权重等，解除下方注释：
     */
    /*
    @Bean
    public IRule myRule() {
        return new com.netflix.loadbalancer.RandomRule(); // 切换为随机算法
    }
    */
}
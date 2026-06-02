package com.bee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {
    @Resource
    private RestTemplate restTemplate;

    // 定义提供者的微服务名称（指向 Nacos 中注册的 spring.application.name）
    private final String PROVIDER_SERVICE_URL = "http://sc-provider";

    @GetMapping("/consumer/call")
    public String callProvider() {
        // 🌟 Ribbon 会自动拦截这个 URL，将 "sc-provider" 替换为真实机器的 IP+端口
        return restTemplate.getForObject(PROVIDER_SERVICE_URL + "/provider/info", String.class);
    }
}
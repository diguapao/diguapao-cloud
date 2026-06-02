package com.bee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/provider/info")
    public String getInfo() {
        return "数据来自提供者端口: " + port + "，时间戳: " + System.currentTimeMillis() / 1000;
    }
}
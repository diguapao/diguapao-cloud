package com.aip;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class AIService {

    @Value("${ai.api-key}")
    private String apiKey;

    @Value("${ai.url}")
    private String apiUrl;

    private final WebClient webClient = WebClient.builder().build();

    public String chat(String prompt) {

        Map<String, Object> body = new HashMap<>();

        body.put("model", "DeepSeek-R1-Distill-Qwen-7B");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> system = new HashMap<>();
        system.put("role", "system");
        system.put("content", "你是一个资深Java架构师");

        Map<String, String> user = new HashMap<>();
        user.put("role", "user");
        user.put("content", prompt);

        messages.add(system);
        messages.add(user);

        body.put("messages", messages);
        body.put("temperature", 0.6);
        body.put("top_p", 0.95);
        body.put("do_sample","True");

        return webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
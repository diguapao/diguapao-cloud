package com.aip;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aip.dto.ChatRequest;
import com.aip.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class AIService {

    @Value("${ai.api-key}")
    private String apiKey;

    @Value("${ai.url}")
    private String apiUrl;

    private final WebClient webClient = WebClient.builder().build();

    public String chat(String prompt, Boolean stream) {

        ChatRequest body = buildRequest(prompt);
        if (null != stream) {
            body.setStream(stream);
        }
        return webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public Flux<String> streamChat(String prompt, Boolean stream) {
        ChatRequest body = buildRequest(prompt);
        if (null != stream) {
            body.setStream(stream);
        }
        return webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .acceptCharset(StandardCharsets.UTF_8)
                .bodyValue(body)
                .retrieve()
                // 获取原始流
                .bodyToFlux(String.class)
                // 开始解析
                .flatMap(chunk -> {
                    try {
                        // 去掉 data:
                        chunk = chunk.replace("data:", "").trim();

                        // 结束标记
                        if ("[DONE]".equals(chunk)) {
                            return Flux.empty();
                        }

                        // 空数据
                        if (chunk.isBlank()) {
                            return Flux.empty();
                        }

                        // JSON解析
                        JSONObject json = JSONUtil.parseObj(chunk);

                        JSONArray choices = json.getJSONArray("choices");

                        if (choices == null || choices.isEmpty()) {
                            return Flux.empty();
                        }

                        JSONObject choice = choices.getJSONObject(0);

                        JSONObject delta = choice.getJSONObject("delta");

                        if (delta == null) {
                            return Flux.empty();
                        }

                        String content = delta.getStr("content");

                        if (content == null) {
                            return Flux.empty();
                        }
                        log.info(content);
                        return Flux.just(content);

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        return Flux.empty();
                    }
                })
                // 过滤空内容
                .filter(StrUtil::isNotBlank);
    }

    private static ChatRequest buildRequest(String prompt) {
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().role("system").content("你是一个资深Java架构师").build());
        messages.add(Message.builder().role("role").content(prompt).build());

        return ChatRequest.builder()
                .model("DeepSeek-R1-Distill-Qwen-7B")
                .messages(messages)
                .temperature(0.7)
                .max_tokens(2048)
                .top_p(0.95)
                .do_sample("True")
                .build();
    }

}
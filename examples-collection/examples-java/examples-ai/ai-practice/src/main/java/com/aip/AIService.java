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
                        // 1. 打印看看：此时 log 出来的应该已经是纯 {} 包裹的 JSON 了，没有 data: 前缀
                        log.info("WebFlux 收到切片: {}", chunk);

                        if (StrUtil.isBlank(chunk) || "null".equals(chunk) || "[DONE]".equals(chunk)) {
                            return Flux.empty();
                        }

                        // 2. 解析 JSON
                        JSONObject json = JSONUtil.parseObj(chunk);
                        JSONArray choices = json.getJSONArray("choices");
                        if (choices == null || choices.isEmpty()) {
                            return Flux.empty();
                        }

                        JSONObject choice = choices.getJSONObject(0);

                        // 3. 🌟 通过 Python 新加的 finish_reason 优雅判断结束
                        String finishReason = choice.getStr("finish_reason");
                        if ("stop".equals(finishReason)) {
                            log.info("👉 模型输出流正常结束");
                            return Flux.empty();
                        }

                        JSONObject delta = choice.getJSONObject("delta");
                        if (delta == null) {
                            return Flux.empty();
                        }

                        String content = delta.getStr("content");
                        if (content == null) {
                            return Flux.empty();
                        }

                        // 清理 DeepSeek 思维链可能遗留的特殊分行符
                        if (content.contains("k>\\n")) {
                            content = content.replace("k>\\\\n", StrUtil.EMPTY);
                        }

                        return Flux.just(content);

                    } catch (Exception e) {
                        // 💡 把 chunk 打印出来，能一眼看清谁不合规
                        log.error("解析切片异常，当前内容为: " + chunk, e);
                        return Flux.empty();
                    }
                })
                // 过滤空内容
                .filter(StrUtil::isNotBlank);
    }

    private static ChatRequest buildRequest(String prompt) {
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().role("system").content("你是一个资深Java架构师").build());
        messages.add(Message.builder().role("user").content(prompt).build());

        return ChatRequest.builder()
                .model("DeepSeek-R1-Distill-Qwen-7B")
                .messages(messages)
                .temperature(0.7)
                .max_new_tokens(2048)
                .top_p(0.95)
                .do_sample("True")
                .build();
    }

}
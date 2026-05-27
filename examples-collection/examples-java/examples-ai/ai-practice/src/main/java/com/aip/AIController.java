package com.aip;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @GetMapping("/chat")
    public String chat(@RequestParam("msg") String msg) {
        return aiService.chat(msg, null);
    }

    /**
     * 响应SSE（Server Sent Events）流
     */
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String chatForStream(@RequestParam("msg") String msg) {
        return aiService.chat(msg, true);
    }

    /**
     * 响应SSE（Server Sent Events）流
     * 解析 SSE
     */
    @GetMapping(value = "/chat/stream/analysisSse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChatAnalysisSse(@RequestParam("msg") String msg) {
        log.info("响应SSE（Server Sent Events）流：{}", msg);
        return aiService.streamChat(msg, true);
    }

}
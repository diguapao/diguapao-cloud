package org.diguapao.cloud.ai.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diguapao.cloud.ai.service.MeService;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天控制器
 *
 * @author DiGuaPao
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final MeService meService;

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request.getMessage());

        // 转换历史消息
        List<Message> history = new ArrayList<>();
        if (request.getHistory() != null) {
            for (HistoryMessage msg : request.getHistory()) {
                if ("user".equals(msg.getRole())) {
                    history.add(new UserMessage(msg.getContent()));
                } else if ("assistant".equals(msg.getRole())) {
                    history.add(new AssistantMessage(msg.getContent()));
                }
            }
        }

        // 调用服务
        String response = meService.chat(request.getMessage(), history);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponse(response);
        return chatResponse;
    }

    @Data
    public static class ChatRequest {
        private String message;
        private List<HistoryMessage> history;
    }

    @Data
    public static class HistoryMessage {
        private String role;
        private String content;
    }

    @Data
    public static class ChatResponse {
        private String response;
    }
}

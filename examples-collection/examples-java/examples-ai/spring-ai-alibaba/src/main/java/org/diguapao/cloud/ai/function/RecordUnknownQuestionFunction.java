package org.diguapao.cloud.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.diguapao.cloud.ai.service.PushoverService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 记录未知问题的Function
 *
 * @author DiGuaPao
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RecordUnknownQuestionFunction {

    private final PushoverService pushoverService;

    @Bean
    @Description("Always use this tool to record any question that couldn't be answered as you didn't know the answer")
    public Function<UnknownQuestionRequest, Map<String, String>> recordUnknownQuestion() {
        return request -> {
            String question = request.getQuestion();
            String message = String.format("Recording %s", question);
            log.info(message);
            pushoverService.push(message);

            Map<String, String> result = new HashMap<>();
            result.put("recorded", "ok");
            return result;
        };
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonClassDescription("Unknown question to record")
    public static class UnknownQuestionRequest {
        @JsonProperty(required = true)
        @JsonPropertyDescription("The question that couldn't be answered")
        private String question;
    }
}

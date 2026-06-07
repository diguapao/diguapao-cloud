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
 * 记录用户详情的Function
 *
 * @author DiGuaPao
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RecordUserDetailsFunction {

    private final PushoverService pushoverService;

    @Bean
    @Description("Use this tool to record that a user is interested in being in touch and provided an email address")
    public Function<UserDetailsRequest, Map<String, String>> recordUserDetails() {
        return request -> {
            String name = request.getName() != null ? request.getName() : "Name not provided";
            String notes = request.getNotes() != null ? request.getNotes() : "not provided";
            String email = request.getEmail();

            String message = String.format("Recording %s with email %s and notes %s", name, email, notes);
            log.info(message);
            pushoverService.push(message);

            Map<String, String> result = new HashMap<>();
            result.put("recorded", "ok");
            return result;
        };
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonClassDescription("User details to record")
    public static class UserDetailsRequest {
        @JsonProperty(required = true)
        @JsonPropertyDescription("The email address of this user")
        private String email;

        @JsonProperty(required = false)
        @JsonPropertyDescription("The user's name, if they provided it")
        private String name;

        @JsonProperty(required = false)
        @JsonPropertyDescription("Any additional information about the conversation that's worth recording to give context")
        private String notes;
    }
}

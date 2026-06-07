package org.diguapao.cloud.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.diguapao.cloud.ai.config.PushoverProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Pushover推送服务
 *
 * @author DiGuaPao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushoverService {

    private final PushoverProperties pushoverProperties;
    private final OkHttpClient httpClient = new OkHttpClient();

    /**
     * 推送消息到Pushover
     *
     * @param text 消息内容
     */
    public void push(String text) {
        RequestBody formBody = new FormBody.Builder()
                .add("token", pushoverProperties.getToken())
                .add("user", pushoverProperties.getUser())
                .add("message", text)
                .build();

        Request request = new Request.Builder()
                .url("https://api.pushover.net/1/messages.json")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                log.info("Pushover notification sent successfully: {}", text);
            } else {
                log.error("Failed to send Pushover notification. Response code: {}", response.code());
            }
        } catch (IOException e) {
            log.error("Error sending Pushover notification", e);
        }
    }
}

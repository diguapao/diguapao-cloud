package org.diguapao.cloud.ai.service;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.text.PDFTextStripper;
import org.diguapao.cloud.ai.config.MeProperties;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallingOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 个人信息聊天服务
 *
 * @author DiGuaPao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MeService {

    private final MeProperties meProperties;
    private final DashScopeChatModel chatModel;
    private final ResourceLoader resourceLoader;

    private String linkedinContent;
    private String summaryContent;

    @PostConstruct
    public void init() {
        loadLinkedinPdf();
        loadSummary();
    }

    /**
     * 加载LinkedIn PDF内容
     */
    private void loadLinkedinPdf() {
        try {
            Resource resource = resourceLoader.getResource(meProperties.getPdfPath());
            if (!resource.exists()) {
                log.warn("LinkedIn PDF not found at: {}, using default content", meProperties.getPdfPath());
                linkedinContent = "LinkedIn profile information not available. Please add a PDF file to provide detailed career information.";
                return;
            }
            try (InputStream inputStream = resource.getInputStream();
                 PDDocument document = PDDocument.class.cast(inputStream)) {
                PDFTextStripper stripper = new PDFTextStripper();
                linkedinContent = stripper.getText(document);
                log.info("Successfully loaded LinkedIn PDF content");
            }
        } catch (IOException e) {
            log.error("Failed to load LinkedIn PDF", e);
            linkedinContent = "LinkedIn content not available";
        }
    }

    /**
     * 加载个人简介
     */
    private void loadSummary() {
        try {
            Resource resource = resourceLoader.getResource(meProperties.getSummaryPath());
            try (InputStream inputStream = resource.getInputStream()) {
                summaryContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                log.info("Successfully loaded summary content");
            }
        } catch (IOException e) {
            log.error("Failed to load summary", e);
            summaryContent = "Summary content not available";
        }
    }

    /**
     * 构建系统提示词
     */
    private String buildSystemPrompt() {
        return String.format(
                "You are acting as %s. You are answering questions on %s's website, " +
                        "particularly questions related to %s's career, background, skills and experience. " +
                        "Your responsibility is to represent %s for interactions on the website as faithfully as possible. " +
                        "You are given a summary of %s's background and LinkedIn profile which you can use to answer questions. " +
                        "Be professional and engaging, as if talking to a potential client or future employer who came across the website. " +
                        "If you don't know the answer to any question, use your recordUnknownQuestion tool to record the question that you couldn't answer, " +
                        "even if it's about something trivial or unrelated to career. " +
                        "If the user is engaging in discussion, try to steer them towards getting in touch via email; " +
                        "ask for their email and record it using your recordUserDetails tool.\n\n" +
                        "## Summary:\n%s\n\n" +
                        "## LinkedIn Profile:\n%s\n\n" +
                        "With this context, please chat with the user, always staying in character as %s.",
                meProperties.getName(), meProperties.getName(), meProperties.getName(),
                meProperties.getName(), meProperties.getName(),
                summaryContent, linkedinContent, meProperties.getName()
        );
    }

    /**
     * 聊天方法
     *
     * @param userMessage 用户消息
     * @param history     历史消息
     * @return AI响应
     */
    public String chat(String userMessage, List<Message> history) {
        List<Message> messages = new ArrayList<>();

        // 添加系统提示词
        messages.add(new SystemMessage(buildSystemPrompt()));

        // 添加历史消息
        if (history != null && !history.isEmpty()) {
            messages.addAll(history);
        }

        // 添加当前用户消息
        messages.add(new UserMessage(userMessage));

        // 配置Function Calling
        FunctionCallingOptions options = FunctionCallingOptions.builder()
                .withFunctions(Set.of("recordUserDetails", "recordUnknownQuestion"))
                .build();

        // 创建Prompt并调用AI
        Prompt prompt = new Prompt(messages, options);
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }
}

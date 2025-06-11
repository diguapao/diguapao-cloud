package org.diguapao.cloud.framework.langchain4j.ai;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class PromptGenerator {
    public static String generatePrompt(String userQuery) {
        OllamaChatModel model = OllamaChatModel.builder()
                .modelName("deepseek-r1:8b")
                .baseUrl("http://localhost:11434")  // Ollama 服务地址
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        ConversationalChain chain = ConversationalChain.builder().chatMemory(chatMemory).chatModel(model).build();
        String promptTemplate = "你是一个图像提示词生成器。根据用户输入生成适合 Stable Diffusion XL 1.0 的提示词，包含主体、风格、细节描述。用户输入: {input}";
        return chain.execute(promptTemplate.replace("{input}", userQuery));
    }
}
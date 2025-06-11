package org.diguapao.cloud.framework.langchain4j.ai;

public class ImageAgent {
    public static void main(String[] args) throws Exception {
        String userQuery = "一只在雪山上的机械熊猫，赛博朋克风格，超高清细节";

        // 1. 生成提示词
        String prompt = PromptGenerator.generatePrompt(userQuery);
        System.out.println("生成的提示词: " + prompt);

        // 2. 生成图片
        String apiUrl = "http://127.0.0.1:8000/generate";
        ImageGenerator.generateImage(apiUrl, prompt, 1920, 1080, 30, 12345);
    }
}
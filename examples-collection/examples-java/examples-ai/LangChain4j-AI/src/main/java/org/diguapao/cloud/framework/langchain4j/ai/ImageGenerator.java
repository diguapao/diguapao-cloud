package org.diguapao.cloud.framework.langchain4j.ai;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageGenerator {
    public static String generateImage(String prompt) {
        String apiUrl = "http://127.0.0.1:8000/generate";  // SDXL Python 服务地址
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiUrl, prompt, String.class);
    }

    public static void generateImage(String baseUrl, String prompt, int width, int height, int steps, int seed) throws Exception {
        // 构建请求参数
        String encodedPrompt = URLEncoder.encode(prompt, "UTF-8");
        String params = String.format("prompt=%s&width=%d&height=%d&steps=%d&seed=%d",
                encodedPrompt, width, height, steps, seed);

        // 构建完整 URL
        String url = baseUrl + "?" + params;

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 发送 GET 请求
        byte[] response = restTemplate.getForObject(url, byte[].class);

        // 保存图片
        Path outputPath = Path.of("output_image.png");
        Files.write(outputPath, response);

        System.out.println("图片已保存至: " + outputPath.toAbsolutePath());
    }
}
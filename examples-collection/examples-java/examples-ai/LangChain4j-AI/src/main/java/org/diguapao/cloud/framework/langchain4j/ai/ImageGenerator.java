package org.diguapao.cloud.framework.langchain4j.ai;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageGenerator {
    public static String generateImage(String prompt) {
        String apiUrl = "http://127.0.0.1:8000/generate";  // SDXL Python 服务地址
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(apiUrl, prompt, String.class);
    }

    public static void generateImage(String baseUrl, String prompt, String negativePrompt,
                                     int width, int height, int steps, int seed) throws IOException {
        // 构建请求参数
        String params = String.format("width=%d" +
                        "&height=%d" +
                        "&steps=%d" +
                        "&seed=%d" +
                        "&guidance_scale=8" +
                        "&num_inference_steps=30" +
                        "&negative_prompt=" + negativePrompt +
                        "&prompt=%s"
                , width, height, steps, seed, prompt);

        // 构建完整 URL
        String url = baseUrl + "?" + params;

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 发送 GET 请求
        byte[] response = restTemplate.getForObject(url, byte[].class);

        // 保存图片
        Path outputPath = Path.of("E:\\DiGuaPao\\gitee\\diguapao-cloud\\examples-collection\\examples-java\\examples-ai\\LangChain4j-AI\\src\\main\\resources\\script\\output_img\\" + System.currentTimeMillis() + "output_image.png");
        Files.write(outputPath, response);

        System.out.println(Thread.currentThread().getName() + " + 图片已保存至: " + outputPath.toAbsolutePath());
    }
}
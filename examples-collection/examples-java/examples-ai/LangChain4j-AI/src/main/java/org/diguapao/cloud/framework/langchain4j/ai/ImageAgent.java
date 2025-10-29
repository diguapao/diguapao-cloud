package org.diguapao.cloud.framework.langchain4j.ai;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ImageAgent {
    public static void main(String[] args) throws Exception {
        String userQuery = "一只在雪山上的机械熊猫，赛博朋克风格，超高清细节";

        // 1. 生成提示词
        String prompt = "<think>\n" +
                "嗯，今天有个用户让我帮忙生成一个图像的提示词，用的是Stable Diffusion XL 1.0。他的输入是：“一只在雪山上的机械熊猫，赛博朋克风格，超高清细节。”我得先分析一下这个需求。\n" +
                "\n" +
                "首先，用户想要的主体是一只机械熊猫，而且是在雪山上。这意味着我需要明确地包含“机械熊猫”和“雪山”的关键词。机械熊猫听起来有点像科幻或者赛博朋克风格，所以风格部分也要注意到。\n" +
                "\n" +
                "接下来，用户提到了赛博朋克风格，这说明整体图像应该有那种独特的美学风格，可能带点未来感和磨损感。我得在提示词里加入“赛博朋克”这样的关键词，同时考虑细节方面，比如材质、颜色。\n" +
                "\n" +
                "然后是超高清细节，这意味着生成的图像需要非常清晰，各个部分都要有丰富的细节。比如机械熊猫的表面可能有复杂的纹理，雪山上可能有冰晶和其他氛围元素。\n" +
                "\n" +
                "我还得考虑如何将这些元素结合起来，让提示词既包含主体又不显得冗长。可能需要分成几个部分：主体、风格和细节描述。这样结构清晰，模型更容易理解并生成出符合预期的图像。\n" +
                "\n" +
                "另外，用户没有提到其他元素，比如时间背景或特定的颜色，但既然是赛博朋克，可能可以暗示一些黑灰色调或者金属质感。所以在细节描述里，可以加上这些方面，让图像更有层次感。\n" +
                "\n" +
                "最后，我需要确保提示词的语言简洁、准确，并且涵盖用户所需的所有关键元素，这样生成的结果才能最接近用户的预期。\n" +
                "</think>\n" +
                "\n" +
                "机械熊猫在雪山上行走，强调其独特的赛博朋克设计，金属外壳覆盖着磨损的装甲片，散发着霓虹蓝光。背景是陡峭的冰山，积雪纷飞，远处有崩塌的冰架和零星的科技建筑。细节上，机械熊猫的表面布满复杂的刻痕和流动的液体管道，雪山上镶嵌着霓虹电路板，整体画面充满未来感与废墟美学，超高清呈现每一个细微纹理。画面中央出现一个时下流行的中国大陆美女，着低胸吊带。";
         prompt = "一幅令人叹为观止的赛里木湖风景画，位于新疆地区，湖水清澈湛蓝，在阳光明媚的天空下反射着周围的山脉。场景设定在黄金时段，温暖的阳光投射出长长的影子，突显了该地区的自然美景。前景是点缀着五彩缤纷野花的郁郁葱葱的草地，延伸至湖边。湖的远端，积雪覆盖的山峰巍峨耸立于地平线上，为构图增添了深度和对比度。湖岸边散布着几座游牧民族的毡房，赋予画面独特的文化元素。风格应为照片写实主义，捕捉环境中每一个细节，从草的纹理到水面的涟漪。图像应当唤起一种宁静与敬畏的感觉，让观者沉浸在这片自然美景中。"
        ;
        //prompt = PromptGenerator.generatePrompt(userQuery);
        System.out.println("生成的提示词: " + prompt);
        String negativePrompt = "低质量，模糊，卡通、动漫、古风风格，不真实的颜色";
        // 2. 生成图片
        String apiUrl = "http://127.0.0.1:8000/generate";
        String finalPrompt = prompt;
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < 100000; i++) {
                        try {
                            ImageGenerator.generateImage(apiUrl, finalPrompt, negativePrompt, 720, 720, 30, 12345);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < 10000; i++) {
                        try {
                            ImageGenerator.generateImage(apiUrl, finalPrompt, negativePrompt, 720, 720, 30, 12345);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })).join();
    }
}
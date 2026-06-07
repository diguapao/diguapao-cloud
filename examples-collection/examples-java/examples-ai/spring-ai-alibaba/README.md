# Spring AI Alibaba Function Calling Demo

这是一个使用 Spring AI Alibaba 实现的 Function Calling 示例项目，模拟了一个基于个人信息的智能聊天机器人。

## 功能特点

- **Function Calling**: 使用 Spring AI 的 Function Calling 能力，实现工具调用
- **PDF 解析**: 读取 LinkedIn PDF 文件内容
- **消息推送**: 集成 Pushover 实现消息推送
- **Web 界面**: 提供简洁的聊天界面

## 技术栈

- Spring Boot 3.2.0
- Spring AI Alibaba 1.0.0-M3.2
- Apache PDFBox 3.0.1
- OkHttp 4.12.0
- Lombok

## 项目结构

```
spring-ai-alibaba/
├── src/main/java/org/diguapao/cloud/ai/
│   ├── SpringAiAlibabaApplication.java      # 启动类
│   ├── config/
│   │   ├── AiConfig.java                    # AI配置
│   │   ├── MeProperties.java                # 个人信息配置
│   │   └── PushoverProperties.java          # Pushover配置
│   ├── controller/
│   │   └── ChatController.java              # 聊天控制器
│   ├── function/
│   │   ├── RecordUserDetailsFunction.java   # 记录用户详情工具
│   │   └── RecordUnknownQuestionFunction.java # 记录未知问题工具
│   └── service/
│       ├── MeService.java                   # 聊天服务
│       └── PushoverService.java             # 推送服务
├── src/main/resources/
│   ├── application.yml                      # 应用配置
│   ├── me/
│   │   ├── linkedin.pdf                     # LinkedIn PDF文件(需自行添加)
│   │   └── summary.txt                      # 个人简介
│   └── static/
│       └── index.html                       # 聊天界面
└── pom.xml
```

## 配置说明

### 1. 配置 API Key

在 `application.yml` 中配置通义千问 API Key：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${DASHSCOPE_API_KEY:your-api-key-here}
```

或者设置环境变量：
```bash
export DASHSCOPE_API_KEY=your-api-key-here
```

### 2. 配置 Pushover（可选）

如果需要使用消息推送功能，配置 Pushover：

```yaml
pushover:
  token: ${PUSHOVER_TOKEN:your-pushover-token}
  user: ${PUSHOVER_USER:your-pushover-user}
```

### 3. 准备个人资料

- 将 LinkedIn PDF 文件放到 `src/main/resources/me/linkedin.pdf`
- 编辑 `src/main/resources/me/summary.txt` 添加个人简介

## 运行项目

### 使用 Maven 运行

```bash
cd spring-ai-alibaba
mvn clean spring-boot:run
```

### 使用 IDE 运行

直接运行 `SpringAiAlibabaApplication.java` 的 main 方法

## 使用方法

1. 启动项目后，访问 http://localhost:8080
2. 在聊天界面中输入问题，AI 会基于配置的个人信息进行回答
3. 当 AI 无法回答问题时，会自动调用 `recordUnknownQuestion` 工具记录
4. 当用户提供联系方式时，会自动调用 `recordUserDetails` 工具记录

## Function Calling 说明

项目实现了两个 Function：

### 1. recordUserDetails
记录用户联系信息，包括：
- email（必需）
- name（可选）
- notes（可选）

### 2. recordUnknownQuestion
记录无法回答的问题：
- question（必需）

这两个 Function 会在适当的时候被 AI 自动调用，并通过 Pushover 发送通知。

## API 接口

### POST /api/chat

请求体：
```json
{
  "message": "用户消息",
  "history": [
    {
      "role": "user",
      "content": "历史消息"
    },
    {
      "role": "assistant",
      "content": "AI回复"
    }
  ]
}
```

响应：
```json
{
  "response": "AI的回复内容"
}
```

## 注意事项

1. 项目使用 Java 17，确保 JDK 版本正确
2. 需要有效的阿里云通义千问 API Key
3. PDF 文件路径需要正确配置
4. Function Calling 需要模型支持（如 qwen-max）

## 扩展建议

1. 添加更多自定义 Function
2. 集成数据库存储对话历史
3. 添加用户认证功能
4. 支持文件上传和解析
5. 添加对话导出功能

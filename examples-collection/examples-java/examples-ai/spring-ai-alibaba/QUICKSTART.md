# 快速开始指南

## 前置要求

- JDK 17+
- Maven 3.6+
- 阿里云通义千问 API Key

## 步骤 1: 配置环境变量

1. 复制环境变量示例文件：
```bash
cp .env.example .env
```

2. 编辑 `.env` 文件，配置你的 API Key：
```bash
DASHSCOPE_API_KEY=你的通义千问API-Key
```

获取 API Key：访问 https://dashscope.console.aliyun.com/apiKey

## 步骤 2: （可选）准备 LinkedIn PDF

如果想要更真实的效果，可以准备一个 LinkedIn PDF 文件：

1. 导出你的 LinkedIn 资料为 PDF
2. 将文件重命名为 `linkedin.pdf`
3. 放到 `src/main/resources/me/` 目录下

> 如果不提供 PDF 文件，程序也能正常运行，只是会使用默认的简介信息。

## 步骤 3: 修改个人信息

编辑 `src/main/resources/me/summary.txt`，替换为你自己的简介信息。

## 步骤 4: 启动应用

### Windows 用户：
```bash
start.bat
```

### Linux/Mac 用户：
```bash
chmod +x start.sh
./start.sh
```

### 或使用 Maven：
```bash
mvn clean spring-boot:run
```

## 步骤 5: 使用应用

1. 打开浏览器访问：http://localhost:8080
2. 在聊天界面输入问题，例如：
   - "Tell me about your experience"
   - "What are your key skills?"
   - "I'd like to get in touch, my email is test@example.com"

## 功能测试

### 测试 Function Calling

1. **测试记录用户信息**：
   - 输入："I want to contact you, my email is john@example.com"
   - AI 会自动调用 `recordUserDetails` 函数

2. **测试记录未知问题**：
   - 输入一个 AI 无法根据提供信息回答的问题
   - AI 会自动调用 `recordUnknownQuestion` 函数

### 查看日志

在控制台可以看到 Function Calling 的执行日志：
```
Tool called: recordUserDetails
Pushover notification sent successfully: Recording John with email john@example.com and notes not provided
```

## 常见问题

### 1. API Key 无效
- 确保已经在阿里云开通了通义千问服务
- 检查 API Key 是否正确复制

### 2. PDF 加载失败
- 确保 PDF 文件路径正确
- 检查 PDF 文件是否损坏
- 如果不需要 PDF，删除或注释掉 `application.yml` 中的 `pdf-path` 配置

### 3. Function Calling 不工作
- 确保使用的是支持 Function Calling 的模型（如 qwen-max）
- 检查 Function 定义是否正确注册

## 扩展开发

### 添加新的 Function

1. 在 `function` 包下创建新的 Function 配置类
2. 使用 `@Bean` 和 `@Description` 注解定义函数
3. 在 `MeService.chat()` 方法中注册新函数名

示例：
```java
@Bean
@Description("Your function description")
public Function<YourRequest, YourResponse> yourFunction() {
    return request -> {
        // Your logic here
        return response;
    };
}
```

### 自定义模型配置

在 `application.yml` 中可以修改模型参数：
```yaml
spring:
  ai:
    dashscope:
      chat:
        options:
          model: qwen-max
          temperature: 0.7
          max-tokens: 2000
```

## API 接口文档

### POST /api/chat

**请求示例：**
```json
{
  "message": "Tell me about your experience",
  "history": [
    {
      "role": "user",
      "content": "Hello"
    },
    {
      "role": "assistant",
      "content": "Hi! How can I help you?"
    }
  ]
}
```

**响应示例：**
```json
{
  "response": "I have over 15 years of experience in software engineering..."
}
```

## 下一步

- 集成数据库存储对话历史
- 添加用户认证
- 实现更多自定义 Function
- 部署到生产环境

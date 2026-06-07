# 项目架构说明

## 整体架构

本项目是一个基于 Spring AI Alibaba 的 Function Calling 演示应用，实现了一个智能聊天机器人，能够根据个人信息回答问题，并在必要时自动调用工具函数。

## 核心组件

### 1. Web 层
- **ChatController**: 处理前端的聊天请求，负责消息格式转换和 HTTP 响应

### 2. 服务层
- **MeService**: 核心聊天服务，负责：
  - 加载和管理个人信息（PDF + 文本）
  - 构建系统提示词
  - 调用 AI 模型进行对话
  - 配置 Function Calling 选项

- **PushoverService**: 消息推送服务，负责：
  - 发送通知到 Pushover
  - 记录工具调用的结果

### 3. Function 层
- **RecordUserDetailsFunction**: 记录用户联系信息的工具函数
- **RecordUnknownQuestionFunction**: 记录无法回答问题的工具函数

### 4. 配置层
- **AiConfig**: AI 模型配置
- **MeProperties**: 个人信息配置属性
- **PushoverProperties**: 推送服务配置属性

## 工作流程

```
用户输入
   ↓
ChatController (接收请求)
   ↓
MeService (处理业务逻辑)
   ↓
构建消息上下文 (系统提示词 + 历史消息 + 当前消息)
   ↓
配置 Function Calling 选项
   ↓
调用 DashScopeChatModel (通义千问)
   ↓
AI 决策：是否需要调用工具
   ↓
├─→ 直接回答 → 返回结果
└─→ 调用工具 → 执行 Function → 返回结果
   ↓
返回响应给前端
```

## Function Calling 机制

### 注册 Function

```java
@Bean
@Description("工具描述")
public Function<RequestType, ResponseType> functionName() {
    return request -> {
        // 执行逻辑
        return response;
    };
}
```

### 配置 Function Calling

```java
FunctionCallingOptions options = FunctionCallingOptions.builder()
    .withFunctions(Set.of("recordUserDetails", "recordUnknownQuestion"))
    .build();

Prompt prompt = new Prompt(messages, options);
```

### Function 自动调用

AI 模型会根据：
1. 用户的输入
2. 对话上下文
3. Function 的描述

自动决定是否调用工具，以及调用哪个工具。

## 数据流

### 请求数据流

```
前端 (index.html)
  ↓ HTTP POST /api/chat
  {
    "message": "用户消息",
    "history": [...]
  }
  ↓
ChatController
  ↓ 转换为 Message 对象
MeService
  ↓ 添加系统提示词
  [SystemMessage, ...history, UserMessage]
  ↓
DashScopeChatModel (AI)
  ↓
ChatResponse
  ↓
提取文本内容
  ↓
返回 JSON
  {
    "response": "AI回复"
  }
  ↓
前端展示
```

### Function 调用流程

```
AI 决定调用工具
  ↓
Spring AI 框架拦截
  ↓
查找对应的 @Bean Function
  ↓
反序列化参数
  ↓
执行 Function 逻辑
  {
    recordUserDetails(email, name, notes)
      ↓
    PushoverService.push(message)
      ↓
    返回 {"recorded": "ok"}
  }
  ↓
将结果返回给 AI
  ↓
AI 基于工具返回结果生成最终回复
```

## 关键技术点

### 1. PDF 解析
使用 Apache PDFBox 读取 LinkedIn PDF：
```java
PDDocument document = PDDocument.load(inputStream);
PDFTextStripper stripper = new PDFTextStripper();
String text = stripper.getText(document);
```

### 2. 系统提示词工程
通过精心设计的系统提示词，让 AI：
- 扮演特定角色
- 了解背景信息
- 知道何时使用工具

### 3. 对话历史管理
维护对话上下文，让 AI 理解完整的对话流程

### 4. Function Calling
使用 Spring AI 的声明式 Function Calling：
- 通过注解定义工具
- 框架自动处理参数映射
- AI 自主决定何时调用

## 扩展点

### 1. 添加新的 Function
在 `function` 包下创建新的配置类

### 2. 集成数据库
使用 Spring Data JPA 存储对话历史

### 3. 添加身份验证
集成 Spring Security

### 4. 多模型支持
配置多个 AI 模型，根据场景选择

### 5. 流式响应
使用 Server-Sent Events 实现打字机效果

## 配置管理

### application.yml
- 服务端口
- AI 模型配置
- Pushover 配置
- 个人信息路径

### .env
- 敏感信息（API Key）
- 环境变量

### 资源文件
- summary.txt: 个人简介
- linkedin.pdf: LinkedIn 资料（可选）
- index.html: 前端界面

## 依赖关系

```
SpringAiAlibabaApplication
  ↓
ChatController
  ↓
MeService
  ↓
├─→ DashScopeChatModel (Spring AI)
├─→ PushoverService
├─→ ResourceLoader (加载资源)
└─→ MeProperties (配置)

RecordUserDetailsFunction
  ↓
PushoverService
  ↓
PushoverProperties (配置)
```

## 性能考虑

1. **PDF 加载**: 在启动时一次性加载到内存
2. **HTTP 连接**: 使用 OkHttp 连接池
3. **对话历史**: 前端管理，避免服务端状态
4. **异步处理**: Pushover 推送不阻塞主流程

## 安全考虑

1. **API Key**: 通过环境变量配置，不提交到代码库
2. **输入验证**: 在 Controller 层进行参数校验
3. **错误处理**: 统一异常处理，避免敏感信息泄露
4. **日志脱敏**: 敏感信息不记录到日志

## 监控和日志

使用 Lombok 的 @Slf4j 注解：
- INFO: 正常业务流程
- WARN: PDF 文件缺失等非致命错误
- ERROR: API 调用失败等错误

建议添加：
- Spring Boot Actuator 健康检查
- Prometheus 指标收集
- ELK 日志聚合

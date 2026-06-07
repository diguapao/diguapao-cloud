#!/bin/bash

# 检查环境变量
if [ ! -f .env ]; then
    echo "警告: .env 文件不存在，请复制 .env.example 并配置"
    exit 1
fi

# 加载环境变量
export $(cat .env | grep -v '^#' | xargs)

# 检查 API Key
if [ "$DASHSCOPE_API_KEY" = "your-dashscope-api-key-here" ] || [ -z "$DASHSCOPE_API_KEY" ]; then
    echo "错误: 请在 .env 文件中配置有效的 DASHSCOPE_API_KEY"
    exit 1
fi

echo "正在启动 Spring AI Alibaba Demo..."
mvn clean spring-boot:run

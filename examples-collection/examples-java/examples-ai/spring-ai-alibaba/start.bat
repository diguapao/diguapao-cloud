@echo off
setlocal enabledelayedexpansion

REM 检查.env文件
if not exist .env (
    echo 警告: .env 文件不存在，请复制 .env.example 并配置
    exit /b 1
)

REM 加载环境变量
for /f "tokens=1,2 delims==" %%a in (.env) do (
    set "%%a=%%b"
)

REM 检查 API Key
if "%DASHSCOPE_API_KEY%"=="your-dashscope-api-key-here" (
    echo 错误: 请在 .env 文件中配置有效的 DASHSCOPE_API_KEY
    exit /b 1
)

if "%DASHSCOPE_API_KEY%"=="" (
    echo 错误: 请在 .env 文件中配置有效的 DASHSCOPE_API_KEY
    exit /b 1
)

echo 正在启动 Spring AI Alibaba Demo...
mvn clean spring-boot:run

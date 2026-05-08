@echo off
chcp 65001 >nul
title Prometheus + Grafana 一键部署脚本 (Windows 11)
color 0A

echo ╔══════════════════════════════════════════════════╗
echo ║      Prometheus + Grafana 一键部署脚本             ║
echo ║      管理员身份打开PowerShell执行脚本               ║
echo ║      适用于 Windows 11                            ║
echo ║      windows_exporter-0.31.6-amd64.msi 需要安装   ║
echo ║      部署时间：2026-05-07 09:42                    ║
echo ╚══════════════════════════════════════════════════╝
echo.

REM ==================== 配置区域 ====================
set "BASE_DIR=E:\work\YMT\soft\monitoring"
set "PROM_DIR=%BASE_DIR%\prometheus"
set "GRAFANA_DIR=%BASE_DIR%\grafana"
set "NSSM_DIR=%BASE_DIR%\nssm"
REM set "LOG_DIR=%BASE_DIR%\logs"

REM 版本号（可根据需要修改）
set "PROM_VERSION=2.53.0"
set "GRAFANA_VERSION=10.4.2"
set "NSSM_VERSION=2.24"

REM 下载链接
set "PROM_URL=https://github.com/prometheus/prometheus/releases/download/v%PROM_VERSION%/prometheus-%PROM_VERSION%.windows-amd64.zip"
set "GRAFANA_URL=https://dl.grafana.com/enterprise/release/grafana-enterprise-%GRAFANA_VERSION%.windows-amd64.msi"
set "NSSM_URL=https://nssm.cc/release/nssm-%NSSM_VERSION%.zip"

REM ==================== 权限检查 ====================
echo [1/8] 检查管理员权限...
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo [错误] 请以管理员身份运行此脚本！
    echo 右键点击本文件，选择"以管理员身份运行"
    pause
    exit /b 1
)
echo [✓] 管理员权限确认通过
echo.

REM ==================== 创建目录结构 ====================
echo [2/8] 创建目录结构...
if not exist "%BASE_DIR%" mkdir "%BASE_DIR%"
if not exist "%PROM_DIR%" mkdir "%PROM_DIR%"
if not exist "%PROM_DIR%\data" mkdir "%PROM_DIR%\data"
if not exist "%PROM_DIR%\logs" mkdir "%PROM_DIR%\logs"
if not exist "%GRAFANA_DIR%" mkdir "%GRAFANA_DIR%"
if not exist "%GRAFANA_DIR%\data" mkdir "%GRAFANA_DIR%\data"
if not exist "%GRAFANA_DIR%\logs" mkdir "%GRAFANA_DIR%\logs"
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"
echo [✓] 目录结构创建完成
echo.

REM ==================== 下载文件 (优化版) ====================
echo [3/8] 检查本地安装包并下载缺失文件...
echo.

REM 定义本地文件路径变量
set "LOCAL_PROMETHEUS_ZIP=%BASE_DIR%\prometheus.zip"
set "LOCAL_GRAFANA_MSI=%BASE_DIR%\grafana.msi"
set "LOCAL_NSSM_ZIP=%BASE_DIR%\nssm.zip"

REM 下载 Prometheus
if exist "%LOCAL_PROMETHEUS_ZIP%" (
    echo [✓] Prometheus: 检测到本地文件 "%LOCAL_PROMETHEUS_ZIP%"，跳过下载
) else (
    echo [↓] 正在下载 Prometheus...
    powershell -Command "Invoke-WebRequest -Uri '%PROM_URL%' -OutFile '%LOCAL_PROMETHEUS_ZIP%'" 2>nul
    if %errorLevel% neq 0 (
        echo [错误] Prometheus 下载失败，请检查网络或手动放入文件
        echo    需要文件：%LOCAL_PROMETHEUS_ZIP%
        pause
        exit /b 1
    ) else (
        echo [✓] Prometheus 下载完成
    )
)

REM 下载 Grafana
if exist "%LOCAL_GRAFANA_MSI%" (
    echo [✓] Grafana: 检测到本地文件 "%LOCAL_GRAFANA_MSI%"，跳过下载
) else (
    echo [↓] 正在下载 Grafana...
    powershell -Command "Invoke-WebRequest -Uri '%GRAFANA_URL%' -OutFile '%LOCAL_GRAFANA_MSI%'" 2>nul
    if %errorLevel% neq 0 (
        echo [错误] Grafana 下载失败，请检查网络或手动放入文件
        echo    需要文件：%LOCAL_GRAFANA_MSI%
        pause
        exit /b 1
    ) else (
        echo [✓] Grafana 下载完成
    )
)

REM 下载 NSSM
if exist "%LOCAL_NSSM_ZIP%" (
    echo [✓] NSSM: 检测到本地文件 "%LOCAL_NSSM_ZIP%"，跳过下载
) else (
    echo [↓] 正在下载 NSSM...
    powershell -Command "Invoke-WebRequest -Uri '%NSSM_URL%' -OutFile '%LOCAL_NSSM_ZIP%'" 2>nul
    if %errorLevel% neq 0 (
        echo [错误] NSSM 下载失败，请检查网络或手动放入文件
        echo    需要文件：%LOCAL_NSSM_ZIP%
        pause
        exit /b 1
    ) else (
        echo [✓] NSSM 下载完成
    )
)

echo.
echo [✓] 所有依赖文件准备就绪
echo.

REM ==================== 解压和安装 ====================
echo [4/8] 解压和安装组件...

REM 解压 Prometheus
if exist "%BASE_DIR%\prometheus.zip" (
    echo 解压 Prometheus...
    powershell -Command "Expand-Archive -Path '%BASE_DIR%\prometheus.zip' -DestinationPath '%BASE_DIR%' -Force" 2>nul
    xcopy /E /Y "%BASE_DIR%\prometheus-%PROM_VERSION%.windows-amd64\*" "%PROM_DIR%\" >nul 2>&1
    rmdir /S /Q "%BASE_DIR%\prometheus-%PROM_VERSION%.windows-amd64" >nul 2>&1
    echo [✓] Prometheus 解压完成
)

REM 解压 NSSM
if exist "%BASE_DIR%\nssm.zip" (
    echo 解压 NSSM...
    powershell -Command "Expand-Archive -Path '%BASE_DIR%\nssm.zip' -DestinationPath '%BASE_DIR%' -Force" 2>nul
    xcopy /E /Y "%BASE_DIR%\nssm-%NSSM_VERSION%\win64\nssm.exe" "%BASE_DIR%\nssm.exe"* >nul 2>&1
    rmdir /S /Q "%BASE_DIR%\nssm-%NSSM_VERSION%" >nul 2>&1
    echo [✓] NSSM 解压完成
)

REM 安装 Grafana (MSI)
if exist "%BASE_DIR%\grafana.msi" (
    echo 安装 Grafana...
    REM 这里传 INSTALLDIR="%GRAFANA_DIR%" 可能没有用
    msiexec /i "%BASE_DIR%\grafana.msi" /quiet /norestart INSTALLDIR="%GRAFANA_DIR%"
    echo [✓] Grafana 安装完成
)
echo.



:: ==================== 配置 Prometheus ====================
echo [5/8] 配置 Prometheus...

:: 创建告警规则目录
if not exist "%PROM_DIR%\rules" mkdir "%PROM_DIR%\rules"

:: 从模板文件复制 prometheus.yml
if exist "%~dp0prometheus.yml.template" (
    copy /Y "%~dp0prometheus.yml.template" "%PROM_DIR%\prometheus.yml" >nul
    echo [OK] prometheus.yml 配置完成
) else (
    echo [错误] 未找到模板文件 prometheus.yml.template
    echo 请确保该文件与脚本在同一目录下
)

:: 从模板文件复制告警规则
if exist "%~dp0windows_alerts.yml.template" (
    copy /Y "%~dp0windows_alerts.yml.template" "%PROM_DIR%\rules\windows_alerts.yml" >nul
    echo [OK] 告警规则配置完成
) else (
    echo [错误] 未找到模板文件 windows_alerts.yml.template
    echo 请确保该文件与脚本在同一目录下
)

echo [✓] Prometheus 配置完成
echo.



REM ==================== 配置 Grafana ====================
echo [6/8] 配置 Grafana...

REM 修改 Grafana 配置文件，没有的话，复制一份安装包里的配置文件到这个目录下
set "GRAFANA_CONF=%GRAFANA_DIR%\grafana\conf\defaults.ini"
if exist "%GRAFANA_CONF%" (
    REM 设置中文界面
    powershell -Command "(Get-Content '%GRAFANA_CONF%') -replace 'default_language = en-US', 'default_language = zh-Hans' | Set-Content '%GRAFANA_CONF%'"
    echo [✓] Grafana 配置完成（已设置中文界面）
) else (
    echo [注意] Grafana 配置文件未找到，使用默认配置
)
echo.

REM ==================== 注册为 Windows 服务 ====================
echo [7/8] 注册为 Windows 服务...

REM --- 强制清理残留服务 (关键修改) ---
echo 正在清理可能存在的残留服务...
sc stop Prometheus >nul 2>&1
sc delete Prometheus >nul 2>&1
sc stop Grafana >nul 2>&1
sc delete Grafana >nul 2>&1
REM 等待系统释放句柄
timeout /t 2 /nobreak >nul

REM 注册 Prometheus 服务
echo 注册 Prometheus 服务...
"%BASE_DIR%\nssm.exe" install Prometheus "%PROM_DIR%\prometheus.exe"
"%BASE_DIR%\nssm.exe" set Prometheus AppDirectory "%PROM_DIR%"
"%BASE_DIR%\nssm.exe" set Prometheus AppParameters "--config.file=prometheus.yml --storage.tsdb.path=data --web.listen-address=:9090 --web.enable-lifecycle"
"%BASE_DIR%\nssm.exe" set Prometheus AppStdout "%PROM_DIR%\logs\prometheus.out.log"
"%BASE_DIR%\nssm.exe" set Prometheus AppStderr "%PROM_DIR%\logs\prometheus.err.log"
"%BASE_DIR%\nssm.exe" set Prometheus Start SERVICE_AUTO_START

:: 配置日志轮转 (每天 100MB)
"%BASE_DIR%\nssm.exe" set Prometheus AppRotateFiles 1
"%BASE_DIR%\nssm.exe" set Prometheus AppRotateBytes 104857600
"%BASE_DIR%\nssm.exe" set Prometheus AppRotateSeconds 0
echo [✓] Prometheus 服务注册完成

REM ==================== 注册 Grafana 服务 ====================
echo 注册 Grafana 服务...

:: 1. 定义核心路径变量 (根据你的实际解压结构调整)
:: 假设 %GRAFANA_DIR% 是解压后的根目录，例如 D:\Apps\grafana-v9.0.0
set "GRAFANA_BIN_PATH=%GRAFANA_DIR%\grafana\bin\grafana-server.exe"
set "GRAFANA_WORK_DIR=%GRAFANA_DIR%\grafana\bin"

:: 2. 检查 grafana.exe 是否存在
if not exist "%GRAFANA_BIN_PATH%" (
    echo [错误] 找不到 Grafana 主程序: "%GRAFANA_BIN_PATH%"
    echo 请检查解压目录是否正确，或是否下载了错误的文件。
    echo 服务注册终止。
    goto :eof
)

:: 3. 确保日志目录存在
if not exist "%GRAFANA_DIR%\logs" mkdir "%GRAFANA_DIR%\logs"

:: 4. 开始注册服务
:: 先移除可能存在的旧服务（防止配置残留），忽略报错
"%BASE_DIR%\nssm.exe" remove Grafana confirm >nul 2>&1

:: 安装服务
"%BASE_DIR%\nssm.exe" install Grafana "%GRAFANA_BIN_PATH%"
if %ERRORLEVEL% neq 0 (
    echo [错误] NSSM 安装服务失败
    goto :eof
)

:: 配置服务参数
"%BASE_DIR%\nssm.exe" set Grafana AppDirectory "%GRAFANA_WORK_DIR%"
"%BASE_DIR%\nssm.exe" set Grafana AppParameters "server"
"%BASE_DIR%\nssm.exe" set Grafana AppStdout "%GRAFANA_DIR%\logs\grafana.out.log"
"%BASE_DIR%\nssm.exe" set Grafana AppStderr "%GRAFANA_DIR%\logs\grafana.err.log"
"%BASE_DIR%\nssm.exe" set Grafana Start SERVICE_AUTO_START

:: 配置日志轮转 (既限制时间，又限制大小。到了24小时 或者 到了100MB，就切分日志文件。)
:: 开启轮转
"%BASE_DIR%\nssm.exe" set Grafana AppRotateFiles 1
:: 设置单个文件大小上限为 100MB (100 * 1024 * 1024 = 104857600)
"%BASE_DIR%\nssm.exe" set Grafana AppRotateBytes 104857600
:: 设置轮转间隔为 0 (禁用按时间轮转，完全按大小触发)
"%BASE_DIR%\nssm.exe" set Grafana AppRotateSeconds 0

echo [✓] Grafana 服务注册完成
echo.




REM ==================== 启动服务 ====================
echo [8/8] 启动服务...

echo 启动 Prometheus 服务...
net start Prometheus >nul 2>&1
if %errorLevel% equ 0 (
    echo [✓] Prometheus 服务启动成功
) else (
    echo [警告] Prometheus 服务启动失败，请检查日志
)

echo 启动 Grafana 服务...
net start Grafana >nul 2>&1
if %errorLevel% equ 0 (
    echo [✓] Grafana 服务启动成功
) else (
    echo [警告] Grafana 服务启动失败，请检查日志
)

echo.
echo ╔══════════════════════════════════════════════════╗
echo ║                  部署完成！                       ║
echo ╠══════════════════════════════════════════════════╣
echo ║                                                  ║
echo ║  Prometheus 地址：http://localhost:9090          ║
echo ║  Grafana 地址：   http://localhost:3000          ║
echo ║  Grafana 默认登录：admin / admin                 ║
echo ║                                                  ║
echo ║  安装目录：%BASE_DIR%                            ║
echo ║  日志目录：%LOG_DIR%                             ║
echo ║                                                  ║
echo ║  两个服务已设置为开机自启                         ║
echo ║                                                  ║
echo ╚══════════════════════════════════════════════════╝
echo.
echo 按任意键打开 Grafana 配置指南...
pause >nul

REM 打开浏览器访问 Grafana
REM start http://localhost:3000

echo.
echo 后续配置步骤：
echo 1. 打开浏览器访问 http://localhost:3000
echo 2. 使用 admin/admin 登录（首次登录会提示修改密码）
echo 3. 添加数据源：选择 Prometheus，URL 填写 http://localhost:9090
echo 4. 导入仪表盘：点击 + 号 -> Import，输入 ID 4701（JVM监控）或 1860（Node Exporter监控）
echo.
echo 如需监控 Windows 系统，请额外安装 windows_exporter：
echo 下载地址：https://github.com/prometheus-community/windows_exporter/releases
echo.
pause
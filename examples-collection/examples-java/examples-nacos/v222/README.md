# 复现 No instances available for sc-provider


#开发者手册
部署版本是 2.2.2

打开git base
```shell
#编译打包
cd /d/DiGuaPao/Github/alibaba/nacos/nacos 
 mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
# 启动(还原出nacos-server.jar：cat nacos_part_* > nacos-server.jar)
/d/DiGuaPao/Github/alibaba/nacos/nacos/distribution/target/nacos-server-2.2.2/nacos/bin/startup.sh -m standalone
# 杀死进程
D:\DiGuaPao\Github\alibaba\nacos\nacos\distribution\bin\shutdown.cmd

```

# 官方手册

Nacos 快速开始
这个快速开始手册是帮忙您快速在您的电脑上，下载、安装并使用 Nacos。

0.版本选择
您可以在Nacos的release notes及博客中找到每个版本支持的功能的介绍，当前推荐的稳定版本为2.2.3。

1.预备环境准备
Nacos 依赖 Java 环境来运行。如果您是从代码开始构建并运行Nacos，还需要为此配置 Maven环境，请确保是在以下版本环境中安装使用:

64 bit OS，支持 Linux/Unix/Mac/Windows，推荐选用 Linux/Unix/Mac。
64 bit JDK 1.8+；下载 & 配置。
Maven 3.2.x+；下载 & 配置。
2.下载源码或者安装包
你可以通过源码和发行包两种方式来获取 Nacos。

从 Github 上下载源码方式
Terminal window
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin

下载编译后压缩包方式
您可以从 最新稳定版本 下载 nacos-server-$version.zip 包。

Terminal window
unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
cd nacos/bin

3.修改配置文件
在2.2.0.1和2.2.1版本时，必须执行此变更，否则无法启动；其他版本为建议设置。

修改conf目录下的application.properties文件。

设置其中的nacos.core.auth.plugin.nacos.token.secret.key值，详情可查看鉴权-自定义密钥.

注意，文档中的默认值SecretKey012345678901234567890123456789012345678901234567890123456789和VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=为公开默认值，可用于临时测试，实际使用时请务必更换为自定义的其他有效值。

4.启动服务器
注：Nacos的运行建议至少在2C4G 60G的机器配置下运行。
Linux/Unix/Mac
启动命令(standalone代表着单机模式运行，非集群模式):

sh startup.sh -m standalone

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

bash startup.sh -m standalone

Windows
启动命令(standalone代表着单机模式运行，非集群模式):

startup.cmd -m standalone

5.服务注册&发现和配置管理
服务注册
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'

服务发现
curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'

发布配置
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=HelloWorld"

获取配置
curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"

6.关闭服务器
Linux/Unix/Mac
sh shutdown.sh

Windows
shutdown.cmd

或者双击shutdown.cmd运行文件。
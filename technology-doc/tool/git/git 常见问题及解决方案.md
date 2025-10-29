# 设置全局邮箱

```shell
git config --global user.email "wukong@163.com"
#验证设置
git config --global user.email
```

# 设置项目邮箱

```shell
# 进入特定的 Git 仓库目录
cd /d/work/rjgf/project/SmartCenter
# 设置局部邮箱
git config user.email "wukong@163.com"
# 验证局部设置
git config user.email
```
# 设置项目作者

```shell
# 进入特定的 Git 仓库目录
cd /d/work/rjgf/project/SmartCenter
# 设置局部作者
git config user.name "DiGuaPao"
# 验证局部作者
git config user.name
```

# Git 修改本地提交记录的作者和邮箱（邮箱错误push被拒绝时使用）

```shell
#查看当前提交的作者信息
git log -1
#修改最近一次提交的作者信息
git commit --amend --author="作者 <您的邮箱>"
#例如：git commit --amend --author="wukong <wukong@163.com>"

```

# 仓库内容过大处理

仓库内容较大，或者仓库中有较大文件，由于 http 协议或者传输数据大小限制导致的，可以通过设置如下参数解决：

```shell
git config --global http.postBuffer 52428800000
git config --global http.version HTTP/1.1
```

网络环境不好，可以通过增加下面的参数，降低失败率：

```shell
git config --global http.lowSpeedLimit 0
git config --global http.lowSpeedTime 99999999
```

git clone 报错“RPC failed”，可进行浅层克隆仓库再更新，例如：

```shell
git clone https://github.com/go-admin-team/go-admin.git --depth 1
cd go-admin
git fetch --unshallow
```

# git SSL certificate problem: unable to get local issuer certificate

一招解决：

```shell
git config --global http.sslVerify false
```

# ssh 命令

##  保活建立 ssh 连接
```shell
# 每隔 60 秒发送一次心跳包，最多发送 999999 次心跳包
ssh root@192.168.11.66 -o ServerAliveInterval=60 -o ServerAliveCountMax=999999
```





# error: RPC failed; curl 56 Recv failure: Connection was reset

*注：内容由AI生成。*

遇到 `error: RPC failed; curl 56 Recv failure: Connection was reset` 等错误通常与网络连接不稳定或数据传输量过大有关。为了在网络状况不佳时不中断 Git 克隆操作，并在网络恢复后继续克隆，你可以采取以下措施：

### 增加 Git 缓冲区大小
有时，默认的 HTTP 传输缓冲区大小不足以处理大文件或大量数据。你可以尝试增加缓冲区大小来提高稳定性：
```bash
git config --global http.postBuffer 524288000  # 设置为500MB
```

### 使用浅克隆（Shallow Clone）
如果你只需要最近的提交历史，可以使用浅克隆来减少下载的数据量：
```bash
git clone --depth 1 https://github.com/springaialibaba/spring-ai-alibaba-examples.git
```
之后，如果需要完整的历史记录，可以在网络条件改善后再获取完整的历史。

### 配置重试机制
Git 不直接支持断点续传功能，但可以通过配置重试次数和延迟来减轻网络问题的影响：
```bash
git config --global retry 5  # 设置重试次数为5次
git config --global transfer.fsckobjects true  # 在接收对象时进行额外检查
```

### 使用代理或VPN
如果网络问题是由于地理限制或防火墙引起的，考虑使用代理或VPN服务可能有助于稳定连接。

### 利用SSH代替HTTPS
使用 SSH 协议而不是 HTTPS 可以避免一些由 SSL/TLS 引起的问题，并且在某些情况下提供更稳定的连接。

### 分步克隆
对于非常大的仓库，可以尝试分步克隆，首先初始化一个空的仓库，然后逐步拉取分支或者特定的提交：
```bash
mkdir spring-ai-alibaba-examples && cd spring-ai-alibaba-examples
git init
git remote add origin https://github.com/springaialibaba/spring-ai-alibaba-examples.git
git fetch --depth 1 origin main
git checkout main
```

### 定期同步
如果你预计网络会不定期中断，可以定期执行同步命令来更新本地仓库的状态，直到整个仓库完全同步完毕。

### 检查服务器状态
有时候，问题可能出在远程服务器端。确保 GitHub 或其他代码托管服务没有正在进行维护或出现故障。

通过上述方法，你可以在一定程度上缓解网络不稳定带来的影响，并尽可能保证 Git 操作的成功率。然而，需要注意的是，Git 本身并不支持真正的断点续传功能，所以在网络彻底中断的情况下，可能仍然需要重新开始部分或全部操作。 

此外，如果你正在使用 SourceTree 或其他图形界面工具，也可以参考相关文档或社区讨论中的建议来调整设置，例如增大 `http.postBuffer` 的值或更改 SSH 客户端选项等 。





# 路径太长怎么办(Filename too long)

在注册表 HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\FileSystem 下的 LongPathsEnabled 配置为 1 重启系统后依然无效则进行如下操作：

配置 Git 忽略 Windows 路径限制

```sh
# 配置
git config --global core.longpaths true
# 验证配置
git config --global --get core.longpaths
```


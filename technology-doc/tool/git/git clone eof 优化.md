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
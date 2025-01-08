# CentOS 7.9 相关记录

## 配置静态IP

我的网络配置文件位于： /etc/sysconfig/network-scripts/ifcfg-ens33
编辑 vi /etc/sysconfig/network-scripts/ifcfg-ens33 键入以下内容

```shell
vi  /etc/sysconfig/network-scripts/ifcfg-ens33

# 键入或修改为以下内容
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
UUID="1eb02003-fbc6-45a1-9581-48864e0275b5"
DEVICE="ens33"
ONBOOT="yes"
IPADDR="192.168.11.101"
PREFIX="24"
GATEWAY="192.168.11.2"
IPV6_PRIVACY="no"
DNS1="8.8.8.8"
DNS2="8.8.4.4"

#配置主机名
vi /etc/hostname
#键入或修改为自己的主机名
k8s01

#配置 IP 地址到主机名映射关系
vi /etc/hosts
#键入或修改为自己的IP 地址到主机名映射关系
192.168.11.101 k8s01

#重启网卡使得设置生效
sudo systemctl restart network

#检查IP
ip addr show ens33

```

## 常见问题

1. 如果在 VMware 中先安装了好一台 CentOS，然后复制出多台 CentOS，则需要修改调网络适配器的 MAC 地址（点生成即可），否则网卡无法正常工作：

![](./img/VMware%20生成机器网卡MAC地址.png)

修改完 MAC 地址后重新开机即可。

2. 如果说 NetworkManager 在运行，那么会导致 network 无法正常工作，需要将其禁用掉：

```shell
sudo systemctl stop NetworkManager && sudo systemctl disable NetworkManager
```

## 如何查看 xxl-job accessToken

在笔者的机器上，xxljob安装目录位于：/usr/local/xxljob/xxl-job-2.4.1/

```shell
#安装目录有如下内容
[root@javaevn xxl-job-admin]# ll /usr/local/xxljob/xxl-job-2.4.1/
total 92
drwxr-xr-x. 4 root root   128 Oct 30 20:10 doc
-rwxr-xr-x. 1 root root 35141 Oct 30 20:10 LICENSE
-rwxr-xr-x. 1 root root   679 Oct 30 20:10 NOTICE
-rwxr-xr-x. 1 root root  4409 Oct 30 20:10 pom.xml
-rwxr-xr-x. 1 root root 42756 Oct 30 20:10 README.md
drwxr-xr-x. 4 root root    64 Oct 30 20:10 xxl-job-admin
drwxr-xr-x. 3 root root    32 Oct 30 20:10 xxl-job-core
drwxr-xr-x. 4 root root   104 Oct 30 20:10 xxl-job-executor-samples

#查看 accessToken
[root@javaevn resources]# cat /usr/local/xxljob/xxl-job-2.4.1/xxl-job-admin/src/main/resources/application.properties |
grep 'accessToken'
xxl.job.accessToken=default_token



```

## 时间

### 显示系统时间

```shell
date
```

### 手动设置系统时间

```shell
sudo date -s "2025-01-08 10:13:40"
```
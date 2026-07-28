# 防火墙

```shell
#Ubuntu 如果使用的是 ufw（Ubuntu 默认防火墙），允许所有端口（入站+出站）一行命令
sudo ufw default allow incoming && sudo ufw default allow outgoing && sudo ufw allow from any && sudo ufw reload

```



# VMware NAT 网络配置

![image-20260728111131438](assets/image-20260728111131438.png)



# pg 库

## 安装

### 1. 安装 PostgreSQL 及常用拓展

sudo apt update
sudo apt install -y postgresql postgresql-contrib

### 2. 启动并设置开机自启

sudo systemctl enable --now postgresql

### 3. 设置数据库密码 (将 'devpassword' 替换为你自己的密码)

sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'devpassword';"

sudo vim /etc/postgresql/16/main/postgresql.conf

找到 listen_addresses，取消注释并改为：listen_addresses = '*'

sudo vim /etc/postgresql/16/main/pg_hba.conf

在文件末尾追加一行：
host all all 0.0.0.0/0 scram-sha-256

重启pg
sudo systemctl restart postgresql

### 本地连接

psql -U postgres -h 127.0.0.1 -p 5432

# ck库

## 安装

### 1. 运行官方一键安装脚本（会提示你输入默认账号 default 的密码，输入即可）

curl https://clickhouse.com | sh

### 2. 将 ClickHouse 安装为系统服务

sudo ./clickhouse install

### 3. 启动并设置开机自启

sudo systemctl enable --now clickhouse-server

### 开启远程连接

ClickHouse 默认也只监听本地 127.0.0.1。

打开配置文件：

Bash
sudo nano /etc/clickhouse-server/config.xml
找到 <listen_host>::</listen_host> 这一行，取消注释（或者添加一行 <listen_host>0.0.0.0</listen_host>）。

重启 ClickHouse：

sudo clickhouse restart

启动

sudo clickhouse start

本地测试连接：
clickhouse-client --user default
# **源码安装**

下载nginx：https://nginx.org/download/nginx-1.29.0.tar.gz

解压至 /opt/nginx-1.26.2-source

编译(configure + make)：

```shell
#!/bin/bash

cd /opt/nginx-1.26.2-source || exit

./configure \
--prefix=/opt/nginx-1.26.2 \
--with-http_ssl_module \
--with-http_v2_module \
--with-http_realip_module \
--with-http_gzip_static_module \
--with-http_stub_status_module \
--with-http_sub_module \
--with-pcre \
--with-stream \
--with-stream_ssl_module \
--with-pcre=/opt/nginxSource/pcre2-10.44 \
--with-zlib=/opt/nginxSource/zlib-1.3.1 \
--with-openssl=/opt/nginxSource/openssl-3.3.2 \
--with-threads

make && sudo make install
```

编译产出物说明：

`--prefix=/opt/nginx-1.26.2`指定了 nginx 的安装目录，此即最终的编译产出物。

结构如下：

```bash
/opt/nginx-1.26.2/
├── conf/               # 配置文件目录
│   ├── nginx.conf      # 主配置文件
│   └── mime.types      # MIME 类型定义
├── html/               # 默认的静态文件目录（包含 index.html 等）
├── logs/               # 日志目录（access.log、error.log、nginx.pid 等）
├── sbin/               # Nginx 可执行文件
│   └── nginx           # 👈 这就是编译后的主程序
└── etc/                # （可选）一些扩展配置目录（不常见）
```

关键产出物说明：

| 文件/目录                           | 说明                                                       |
| ----------------------------------- | ---------------------------------------------------------- |
| `/opt/nginx-1.26.2/sbin/nginx`      | **Nginx 的可执行文件**，启动服务时使用                     |
| `/opt/nginx-1.26.2/conf/nginx.conf` | 默认的 Nginx 主配置文件                                    |
| `/opt/nginx-1.26.2/html/`           | 默认的网站根目录，存放 `index.html`                        |
| `/opt/nginx-1.26.2/logs/`           | Nginx 运行日志目录（包括 access.log、error.log、pid 文件） |

验证产出物：

```shell
# 查看版本
/opt/nginx-1.26.2/sbin/nginx -v
# 查看模块
/opt/nginx-1.26.2/sbin/nginx -V
```

启动 nginx：

```shell
/opt/nginx-1.26.2/sbin/nginx
```

验证配置是否正确：

```she
/opt/nginx-1.26.2/sbin/nginx -t
```

刷新nginx配置：

```shell
# /opt/nginx-1.26.2/conf/nginx.conf 发生修改后平滑应用新配置（再此之前建议先执行：/opt/nginx-1.26.2/sbin/nginx -t）
/opt/nginx-1.26.2/sbin/nginx -s reload
```




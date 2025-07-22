# **源码安装**

下载nginx：https://nginx.org/download/nginx-1.29.0.tar.gz

解压至 /opt/nginx-1.29.0-source

编译(configure + make)：

```shell
#!/bin/bash

cd /opt/nginx-1.29.0-source || exit

./configure \
--prefix=/opt/nginx-1.29.0 \
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

`--prefix=/opt/nginx-1.29.0`指定了 nginx 的安装目录，此即最终的编译产出物。

结构如下：

```bash
/opt/nginx-1.29.0/
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
| `/opt/nginx-1.29.0/sbin/nginx`      | **Nginx 的可执行文件**，启动服务时使用                     |
| `/opt/nginx-1.29.0/conf/nginx.conf` | 默认的 Nginx 主配置文件                                    |
| `/opt/nginx-1.29.0/html/`           | 默认的网站根目录，存放 `index.html`                        |
| `/opt/nginx-1.29.0/logs/`           | Nginx 运行日志目录（包括 access.log、error.log、pid 文件） |

验证产出物：

```shell
# 查看版本
/opt/nginx-1.29.0/sbin/nginx -v
# 查看模块
/opt/nginx-1.29.0/sbin/nginx -V
```

启动 nginx：

```shell
/opt/nginx-1.29.0/sbin/nginx
```

验证配置是否正确：

```she
/opt/nginx-1.29.0/sbin/nginx -t
```

刷新nginx配置：

```shell
# /opt/nginx-1.29.0/conf/nginx.conf 发生修改后平滑应用新配置（再此之前建议先执行：/opt/nginx-1.29.0/sbin/nginx -t）
/opt/nginx-1.29.0/sbin/nginx -s reload
```



# 如何为nginx添加新模块

在采用源码安装的情况下，添加新模块需要通过重新编译安装来实现。例如，下面为nginx添加 --with-http_sub_module 模块，用于对响应内容进行字符串替换。

如果编译安装前 /opt/nginx-1.29.0 指向的是旧版，需先***备份***：

```shell
mv  /opt/nginx-1.29.0/ /opt/nginx-1.29.0_back_20250722
```

编译安装脚本如下：

```shell
#!/bin/bash

cd /opt/nginx-1.29.0-source || exit

./configure \
--prefix=/opt/nginx-1.29.0 \
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

安装完成回复旧的配置和部署的内容：

```shell
mv /opt/nginx-1.29.0/conf /opt/nginx-1.29.0/conf_back
mv /opt/nginx-1.29.0/html /opt/nginx-1.29.0/html_back
cp -r /opt/nginx-1.29.0_back_20250722/conf/ /opt/nginx-1.29.0/conf/
cp -r /opt/nginx-1.29.0_back_20250722/html/ /opt/nginx-1.29.0/html/
```

然后重启nginx：

```shell
# 退出进程
/opt/nginx-1.26.2/sbin/nginx -s quit
# 启动进程
/opt/nginx-1.26.2/sbin/nginx
```

# 替换字符串的配置

例如，将nginx响应的 html 文件中的 DEMO_TARGET 替换为 [{"name": "张三","tel": "13455556666"},{"name": "李四","tel": "15677778888"}]，配置如下：

```conf
 location /demo {
            add_header Cache-Control no-cache;
            add_header Cache-Control no-store;
            # 允许对 HTML 内容进行替换
            #sub_filter_types text/html;
            sub_filter 'DEMO_TARGET' '[{"name": "张三","tel": "13455556666"},{"name": "李四","tel": "15677778888"}]';
            # 替换一次
            sub_filter_once on;
            root        html;
            index  login.html index.html;
            try_files $uri $uri/ /demo/index.html;
        }
```


# 与springboot集成

## 引入依赖

```xml

<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>

```

## 配置插件

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>3.4.0-RC1</version>
        </plugin>
        <plugin>
            <groupId>com.github.ulisesbocchio</groupId>
            <artifactId>jasypt-maven-plugin</artifactId>
            <version>${jasypt.version}</version>
            <configuration>
                <path>file:src/main/resources/application.yml</path>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>3.3.1</version>
            <configuration>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
    </plugins>
</build>

```

## 加密前的 src/main/resources/application.yml 如下

```yaml
# 这里以 rabbitmq 为例
rabbitmq:
  config-map:
    upms-plugin:
      addresses: 192.168.1.10:5672,192.168.1.11:5672,192.168.1.12:5672
      username: guest
      password: DEC(123456)
      virtual-host: guest
      upms-plugin-notice:
        exchange: guest.exchange
        queue: guest.queue.${spring.profiles.active}
        group: guest.group

```

## 生成密文

```shell
# 执行这个命令后，src/main/resources/application.yml 文件将被更改，同时控制台日志会输出被加密的密文，不带 -X 参数则无法看到加密日志
 mvn jasypt:encrypt -Djasypt.encryptor.password="sc" -X
# 或，二者等价
 mvn jasypt:encrypt -Djasypt.encryptor.password="sc" -Djasypt.plugin.path=src/main/resources/application.yml -X 
# 日志如下
[DEBUG] Converting value 123456 to ENC(KZaoZdg0+Ipdhnqc9lTmM3iGNU98W06jMZoJFtKOHONUwbhUDDGCTSgzyvGgF+ve)

```

## 加密后的 src/main/resources/application.yml 如下

```yaml
# 这里以 rabbitmq 为例
rabbitmq:
  config-map:
    upms-plugin:
      addresses: 192.168.1.10:5672,192.168.1.11:5672,192.168.1.12:5672
      username: guest
      password: ENC(KZaoZdg0+Ipdhnqc9lTmM3iGNU98W06jMZoJFtKOHONUwbhUDDGCTSgzyvGgF+ve)
      virtual-host: guest
      upms-plugin-notice:
        exchange: guest.exchange
        queue: guest.queue.${spring.profiles.active}
        group: guest.group

```

## 启动项目

启动项目时需要指定JVM参数：-Djasypt.encryptor.password=sc
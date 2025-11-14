package org.diguapao.cloud.jasypt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 演示 Jasypt 加解密
 * 注意： idea 的应用配置 Shorten command line 选择 JAR manifest
 *
 * @author DiGUaPao
 * @version 1.0
 * @since 2025-02-13 15:59:03
 */
@Getter
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JasyptApplication.class,
        properties = {
                "spring.profiles.active=local_pl",
                // 注意：jasypt.encryptor.password的值和ENC(?)加密的值不匹配启动会报崔
                 "jasypt.encryptor.password=examples-jasypt",
        }
)
public class JasyptSecretTest {
    @Autowired
    private StringEncryptor encryptor;
    @Value("${rabbitmq.config-map.upms-plugin.password:}")
    private String pwd;

    /**
     * 加/解密
     */
    @Test
    public void encrypt() {
        String naPwd = "123456";
        // 加密
        String encrypt = encryptor.encrypt(naPwd);
        // 解密
        String decrypt = encryptor.decrypt(encrypt);
        System.out.println("加密后：" + encrypt);
        System.out.println("解密后：" + decrypt);
    }

    /**
     * 解密通过“mvn jasypt:encrypt -Djasypt.encryptor.password=examples-jasypt”命令加密配置文件里的密码
     */
    @Test
    public void decrypt2() {
        System.out.println("解密后：" + pwd);
    }


}
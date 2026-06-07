package org.diguapao.cloud.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Pushover配置属性
 *
 * @author DiGuaPao
 */
@Data
@Component
@ConfigurationProperties(prefix = "pushover")
public class PushoverProperties {
    private String token;
    private String user;
}

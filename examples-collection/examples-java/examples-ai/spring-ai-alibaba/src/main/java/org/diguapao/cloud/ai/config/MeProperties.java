package org.diguapao.cloud.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 个人信息配置属性
 *
 * @author DiGuaPao
 */
@Data
@Component
@ConfigurationProperties(prefix = "me")
public class MeProperties {
    private String name;
    private String pdfPath;
    private String summaryPath;
}

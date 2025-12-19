package org.diguapao.cloud.framework.rocketmq.core.event.producer;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.utils.ThreadUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.diguapao.cloud.framework.rocketmq.core.event.bean.ShuDaoMountEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link org.diguapao.cloud.framework.rocketmq.core.event.bean.ShuDaoMountEvent} 生产者
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-19 14:06:27
 */
@Slf4j
@Component
public class ShuDaoMountProducer {
    @Value("${rocketmq.producer.examples.rocketmq.topic:examples-rocketmq-topic}")
    private String topic;
    // @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final ScheduledExecutorService scanExecutorService = ThreadUtils.newScheduledThreadPool(1,
            new BasicThreadFactory.Builder().namingPattern("ShuDaoMountProducer").daemon(true).build());

    public void send(String msg) {
        ShuDaoMountEvent event = ShuDaoMountEvent.builder().message(StrUtil.isBlank(msg) ? "劳资子数到三" : msg).keys(IdUtil.getSnowflakeNextIdStr()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, event);
        log.info("sendResult={}", JSONUtil.toJsonStr(sendResult));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.scanExecutorService.scheduleAtFixedRate(() -> {
            try {
                this.send(null);
            } catch (Exception e) {
                log.warn("", e);
            }
        }, 5 * 1000, 2 * 1000, TimeUnit.MILLISECONDS);
    }

}

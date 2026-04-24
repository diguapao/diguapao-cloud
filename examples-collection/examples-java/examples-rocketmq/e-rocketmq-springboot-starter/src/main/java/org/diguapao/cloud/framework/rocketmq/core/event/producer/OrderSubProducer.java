package org.diguapao.cloud.framework.rocketmq.core.event.producer;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.utils.ThreadUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.diguapao.cloud.framework.rocketmq.core.entity.Order;
import org.diguapao.cloud.framework.rocketmq.core.event.bean.OrderSubEvent;
import org.diguapao.cloud.framework.rocketmq.core.event.bean.ShuDaoMountEvent;
import org.diguapao.cloud.framework.rocketmq.core.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link OrderSubEvent} 生产者
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-26 14:06:27
 */
@Slf4j
@Component
public class OrderSubProducer {
    @Value("${rocketmq.producer.order.sub.topic.corePoolSize:16}")
    private int corePoolSize ;;
    @Value("${rocketmq.producer.order.sub.topic:order-sub-topic}")
    private String topic;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private OrderService orderService;

    private final ScheduledExecutorService scanExecutorService = ThreadUtils.newScheduledThreadPool(corePoolSize,
            new BasicThreadFactory.Builder().namingPattern("OrderSubProducer").daemon(true).build());

    public void send(Order order) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, OrderSubEvent.builder().order(order).keys(IdUtil.getSnowflakeNextIdStr()).build());
        log.info("sendResult={}", JSONUtil.toJsonStr(sendResult));
    }

    public void asyncSend() {
        Order order = orderService.createOrder(666666L, "地瓜泡");
        OrderSubEvent event = OrderSubEvent.builder().order(order).keys(IdUtil.getSnowflakeNextIdStr()).build();
        rocketMQTemplate.asyncSend(topic, event, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("asyncSendResult={}", JSONUtil.toJsonStr(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                log.warn("asyncSendException", e);
            }
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.scanExecutorService.scheduleAtFixedRate(() -> {
            try {
                this.asyncSend();
            } catch (Exception e) {
                log.warn("", e);
            }
        }, 5 * 1000, 1, TimeUnit.MILLISECONDS);
    }

}

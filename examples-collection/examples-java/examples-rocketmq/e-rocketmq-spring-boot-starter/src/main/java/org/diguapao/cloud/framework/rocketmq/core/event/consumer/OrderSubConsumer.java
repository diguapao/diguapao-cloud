package org.diguapao.cloud.framework.rocketmq.core.event.consumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.diguapao.cloud.framework.rocketmq.core.entity.Order;
import org.diguapao.cloud.framework.rocketmq.core.event.bean.OrderSubEvent;
import org.diguapao.cloud.framework.rocketmq.core.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * {@link OrderSubEvent} 消费者
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-26 14:07:55
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = "${rocketmq.consumer.order.sub.topic:order-sub-topic}",
        consumerGroup = "${rocketmq.consumer.order.sub.group:order-sub-group}",
        consumeThreadNumber = 64
)
public class OrderSubConsumer implements RocketMQListener<OrderSubEvent> {
    @Resource
    private OrderService orderService;

    @Override
    public void onMessage(OrderSubEvent event) {
        boolean exception = false;
        log.info("received message:{} ", JSONUtil.toJsonStr(event));
        Order order = event.getOrder();
        try {
            orderService.save(order);
        } catch (Exception e) {
            log.warn("save order error：{}", order.getOrderNo(), e);
            exception = true;
        }
        if (!exception) {
            log.info("save order success：{}", order.getOrderId());
        }
    }

}

package org.diguapao.cloud.framework.rocketmq.core.event.consumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.diguapao.cloud.framework.rocketmq.core.event.bean.ShuDaoMountEvent;
import org.springframework.stereotype.Service;

/**
 * {@link org.diguapao.cloud.framework.rocketmq.core.event.bean.ShuDaoMountEvent} 消费者
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-19 14:07:55
 */
@Slf4j
@Service
 @RocketMQMessageListener(
         topic = "${rocketmq.consumer.examples.rocketmq.topic:examples-rocketmq-topic}",
         consumerGroup = "${rocketmq.consumer.examples.rocketmq.group:examples-rocketmq-group}",
         consumeThreadNumber = 64
 )
public class ShuDaoMountConsumer implements RocketMQListener<ShuDaoMountEvent> {

    @Override
    public void onMessage(ShuDaoMountEvent event) {
        log.info("received message:{} ", JSONUtil.toJsonStr(event));
    }

}

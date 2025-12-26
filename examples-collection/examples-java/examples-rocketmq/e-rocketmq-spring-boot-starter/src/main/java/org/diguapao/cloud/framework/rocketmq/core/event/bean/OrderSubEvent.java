package org.diguapao.cloud.framework.rocketmq.core.event.bean;

import lombok.*;
import org.diguapao.cloud.framework.rocketmq.core.entity.Order;

import java.io.Serializable;

/**
 * 挺淡提交事件
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-26 11:20:53
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String keys;
    private Order order;

}

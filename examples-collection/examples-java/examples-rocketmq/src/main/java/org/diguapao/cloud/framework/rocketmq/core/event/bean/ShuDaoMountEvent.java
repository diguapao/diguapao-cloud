package org.diguapao.cloud.framework.rocketmq.core.event.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 蜀道山事件
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-19 11:20:53
 */
@Data
@Builder
@ToString
public class ShuDaoMountEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String keys;
    private String message;

}

package org.diguapao.cloud.framework.rocketmq.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.diguapao.cloud.framework.rocketmq.core.entity.Order;

/**
 * 订单 Mapper 接口
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025-12-26 15:49:32
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

package org.diguapao.cloud.framework.rocketmq.core.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.diguapao.cloud.framework.rocketmq.core.entity.Order;
import org.diguapao.cloud.framework.rocketmq.core.entity.OrderItem;
import org.diguapao.cloud.framework.rocketmq.core.entity.OrderLog;
import org.diguapao.cloud.framework.rocketmq.core.mapper.OrderMapper;
import org.diguapao.cloud.framework.rocketmq.enums.OrderStatus;
import org.diguapao.cloud.framework.rocketmq.enums.PayMethod;
import org.diguapao.cloud.framework.rocketmq.utils.OrderNoGeneratorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 订单 Service 类
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025/12/26 15:32
 */
@Service
@RequiredArgsConstructor
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    /**
     * 创建包含三个渔具商品的订单
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @return 创建的订单
     */
    @Transactional
    public Order createOrder(Long userId, String userName) {

        // 1. 生成订单号
        String orderNo = genOrderNo();

        // 2. 创建订单实体
        Order order = Order.builder()
                .orderNo(orderNo)
                .status(OrderStatus.PENDING_PAY)
                .userId(userId)
                .userName(userName)
                .userPhone("13800138000")
                .totalQuantity(3) // 3个商品
                .originalAmount(new BigDecimal("588.00"))
                .discountAmount(new BigDecimal("50.00"))
                .shippingFee(new BigDecimal("15.00"))
                .payAmount(new BigDecimal("553.00"))
                .payMethod(PayMethod.WECHAT_PAY.getCode())
                .payExpireTime(LocalDateTime.now().plusMinutes(30)) // 30分钟后过期
                .receiverName("张三")
                .receiverPhone("13800138000")
                .receiverAddress("北京市朝阳区渔具大街888号")
                .userRemark("请尽快发货，周末要去钓鱼")
                .build();

        // 保存订单

        // 3. 创建三个渔具商品
        List<OrderItem> items = Arrays.asList(
                // 钓鱼竿
                createOrderItem(order, 1L, 101L, "黑坑战斗竿 4.5米",
                        "https://example.com/fishing_rod.jpg", "硬度:8H,长度:4.5米",
                        new BigDecimal("299.00"), 1, new BigDecimal("20.00")),

                // 鱼护
                createOrderItem(order, 2L, 102L, "不锈钢竞技鱼护 40cm",
                        "https://example.com/fish_basket.jpg", "材质:不锈钢,直径:40cm",
                        new BigDecimal("149.00"), 1, new BigDecimal("15.00")),

                // 饵料
                createOrderItem(order, 3L, 103L, "野战蓝鲫饵料 500g",
                        "https://example.com/bait.jpg", "味型:腥香,净重:500g",
                        new BigDecimal("28.00"), 5, new BigDecimal("15.00")) // 5包饵料
        );

        // 保存商品

        // 4. 创建操作日志
        OrderLog orderLog = OrderLog.builder()
                .orderId(order.getOrderId())
                .orderNo(order.getOrderNo())
                .operationType(OrderLog.OperationType.CREATE_ORDER)
                .fromStatus(null)
                .toStatus(OrderStatus.PENDING_PAY.name())
                .operationContent("用户创建渔具订单，包含钓鱼竿、鱼护、饵料")
                .operatorType(OrderLog.OperatorType.USER)
                .operatorId(userId)
                .operatorName(userName)
                .ipAddress("192.168.1.100")
                .operationDetail("{\"items\":[\"钓鱼竿\",\"鱼护\",\"饵料\"],\"totalAmount\":553.00}")
                .build();

        order.setOrderItems(items);
        order.setOrderLogs(Collections.singletonList(orderLog));

        return order;
    }

    /**
     * 创建订单商品
     */
    private OrderItem createOrderItem(Order order, Long productId, Long skuId,
                                      String productName, String productImage,
                                      String specifications, BigDecimal unitPrice,
                                      Integer quantity, BigDecimal discount) {

        BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
        BigDecimal actualAmount = totalPrice.subtract(discount);

        return OrderItem.builder()
                .orderId(order.getOrderId())
                .orderNo(order.getOrderNo())
                .productId(productId)
                .skuId(skuId)
                .productName(productName)
                .productImage(productImage)
                .specifications(specifications)
                .unitPrice(unitPrice)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .discountAmount(discount)
                .actualAmount(actualAmount)
                .build();
    }

    /**
     * 生成订单号
     */
    public static String genOrderNo() {
        return OrderNoGeneratorUtil.generate();
    }

}
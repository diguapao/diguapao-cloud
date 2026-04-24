package org.diguapao.cloud.framework.rocketmq.enums;

/**
 * 订单状态（核心状态机）
 *
 * @author diguapao
 */
public enum OrderStatus {
    PENDING_PAY,     // 待付款
    PAID,            // 已付款
    PROCESSING,      // 处理中
    SHIPPED,         // 已发货
    DELIVERED,       // 已送达
    CONFIRMED,       // 已确认收货
    CANCELLED,       // 已取消
    REFUNDING,       // 退款中
    REFUNDED,        // 已退款
    COMPLETED        // 已完成
}
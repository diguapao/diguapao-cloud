package org.diguapao.cloud.framework.rocketmq.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 高并发订单号生成器（基于雪花算法思想，但用时间字符串替代毫秒）
 *
 * @author diguapao
 */
public class OrderNoGeneratorUtil {

    // 机器ID（建议从配置中心获取，范围 0-999）
    private final long machineId;

    // 序列号（同一毫秒内自增）
    private final AtomicLong sequence = new AtomicLong(0);

    // 上一次生成的时间（格式：yyyyMMddHHmmssSSS）
    private volatile String lastTimestamp = "";

    // 业务前缀，如 "ORD"
    private final String prefix;

    public OrderNoGeneratorUtil(String prefix, long machineId) {
        if (machineId < 0 || machineId > 999) {
            throw new IllegalArgumentException("machineId must be between 0 and 999");
        }
        this.prefix = prefix;
        this.machineId = machineId;
    }

    public synchronized String nextOrderNo() {
        String timestamp = getCurrentTime();

        if (timestamp.equals(lastTimestamp)) {
            // 同一毫秒内，序列号自增
            long seq = sequence.incrementAndGet();
            if (seq > 999) {
                // 超过999，等待下一毫秒（实际几乎不会发生）
                while (getCurrentTime().equals(timestamp)) {
                    Thread.yield();
                }
                // 递归重试
                return nextOrderNo();
            }
            return buildOrderNo(timestamp, seq);
        } else {
            // 新的时间戳，重置序列号
            lastTimestamp = timestamp;
            sequence.set(0);
            return buildOrderNo(timestamp, 0);
        }
    }

    private String buildOrderNo(String timestamp, long seq) {
        return prefix +
                timestamp +
                String.format("%03d", machineId) +
                String.format("%03d", seq);
    }

    private String getCurrentTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    // 单例（可根据需要改为 Spring Bean）
    private static final OrderNoGeneratorUtil INSTANCE = new OrderNoGeneratorUtil("ORD", getMachineId());

    public static String generate() {
        return INSTANCE.nextOrderNo();
    }

    // 简单机器ID生成（生产环境应从配置读取）
    private static long getMachineId() {
        try {
            // 使用 IP 最后一段 或 随机数（确保集群唯一）
            return (System.currentTimeMillis() % 1000);
        } catch (Exception e) {
            return 1;
        }
    }

}
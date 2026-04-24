package org.diguapao.cloud.framework.rocketmq.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单操作日志实体
 * 记录订单状态变更、关键操作等历史记录
 *
 * @author diguapao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_order_log")
public class OrderLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * SQLite 的 JDBC 驱动（如 org.xerial:sqlite-jdbc）不支持获取自增主键（Generated Keys）功能，因此设置 type 为 IdType.NONE 或 IdType.ASSIGN_ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long logId;

    /**
     * 关联的订单ID
     */
    private Long orderId;

    /**
     * 订单编号（冗余字段，便于直接查询）
     */
    private String orderNo;

    /**
     * 操作类型
     */
    public enum OperationType {
        CREATE_ORDER,           // 创建订单
        UPDATE_ORDER,           // 修改订单
        PAYMENT_SUCCESS,        // 支付成功
        PAYMENT_FAILED,         // 支付失败
        SHIP_ORDER,             // 发货
        UPDATE_SHIPPING,        // 更新物流
        CONFIRM_RECEIPT,        // 确认收货
        APPLY_REFUND,           // 申请退款
        APPROVE_REFUND,         // 同意退款
        REJECT_REFUND,          // 拒绝退款
        COMPLETE_REFUND,        // 完成退款
        CANCEL_ORDER,           // 取消订单
        SYSTEM_AUTO_CANCEL,     // 系统自动取消
        ADD_REMARK,             // 添加备注
        UPDATE_ADDRESS,         // 修改地址
        APPLY_INVOICE,          // 申请发票
        SEND_INVOICE,           // 寄送发票
        SYSTEM_PROCESS,         // 系统处理
        MANUAL_INTERVENTION     // 人工干预
    }

    private OperationType operationType;

    /**
     * 操作前订单状态
     */
    private String fromStatus;

    /**
     * 操作后订单状态
     */
    private String toStatus;

    /**
     * 操作内容/描述
     */
    private String operationContent;

    /**
     * 操作详情（JSON格式，存储变更的详细信息）
     */
    private String operationDetail;

    /**
     * 操作人类型
     */
    public enum OperatorType {
        USER,       // 用户
        SYSTEM,     // 系统
        ADMIN,      // 管理员
        MERCHANT,   // 商家
        CUSTOMER_SERVICE  // 客服
    }

    private OperatorType operatorType = OperatorType.USER;

    /**
     * 操作人ID（根据operatorType对应不同表）
     */
    private Long operatorId;

    /**
     * 操作人名称（冗余字段）
     */
    private String operatorName;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 用户代理（User-Agent）
     */
    private String userAgent;

    /**
     * 设备信息
     */
    private String deviceInfo;

    /**
     * 操作结果
     */
    public enum OperationResult {
        SUCCESS,    // 成功
        FAILED,     // 失败
        PENDING,    // 处理中
        CANCELLED   // 已取消
    }

    private OperationResult operationResult = OperationResult.SUCCESS;

    /**
     * 失败原因/错误信息
     */
    private String errorMessage;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 请求参数（JSON格式）
     */
    private String requestParams;

    /**
     * 响应结果（JSON格式）
     */
    private String responseResult;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;

    /**
     * 日志级别
     */
    public enum LogLevel {
        INFO,       // 信息
        WARN,       // 警告
        ERROR,      // 错误
        DEBUG       // 调试
    }

    private LogLevel logLevel = LogLevel.INFO;

    /**
     * 业务模块
     */
    private String businessModule = "order";

    /**
     * 业务子模块
     */
    private String subModule;

    /**
     * 是否通知用户：0-否 1-是
     */
    private Boolean notifyUser = false;

    /**
     * 通知方式
     */
    private String notifyMethod;

    /**
     * 通知内容
     */
    private String notifyContent;

    /**
     * 是否已读（针对管理端）
     */
    private Boolean read = false;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 扩展字段（JSON格式）
     */
    private String extData;

    /**
     * 是否有效：0-无效 1-有效（用于软删除）
     */
    private Boolean valid = true;
}
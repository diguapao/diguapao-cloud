package org.diguapao.cloud.framework.rocketmq.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.diguapao.cloud.framework.rocketmq.enums.OrderStatus;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体类
 * 反映一个完整的订单应该具备的属性
 *
 * @author diguapao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /* ========== 订单基础信息 ========== */

    /**
     * 订单主键ID
     * SQLite 的 JDBC 驱动（如 org.xerial:sqlite-jdbc）不支持获取自增主键（Generated Keys）功能，因此设置 type 为 IdType.NONE 或 IdType.ASSIGN_ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;

    /**
     * 订单编号（业务唯一标识，通常有特定规则）
     */
    private String orderNo;

    /**
     * 订单类型：1-普通订单 2-团购订单 3-秒杀订单 4-预售订单 5-服务订单
     */
    @NotNull
    private Integer orderType;

    /**
     * 订单来源：1-APP 2-小程序 3-PC网站 4-H5 5-线下
     */
    private Integer source;

    private OrderStatus status;

    /**
     * 订单备注（用户填写）
     */
    private String userRemark;

    /**
     * 内部备注（客服或运营人员填写）
     */
    private String internalRemark;


    /* ========== 用户与商户信息 ========== */

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 用户名（冗余字段，避免频繁联表查询）
     */
    private String userName;

    /**
     * 用户手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String userPhone;

    /**
     * 商家/店铺ID
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;


    /* ========== 商品与价格信息 ========== */

    /**
     * 订单商品总数量
     */
    @Min(1)
    private Integer totalQuantity;

    /**
     * 商品原总价（单位：元）
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal originalAmount;

    /**
     * 商品折扣总金额
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal discountAmount;

    /**
     * 运费
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal shippingFee;

    /**
     * 订单实付金额（最终支付金额）
     */
    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal payAmount;

    /**
     * 使用的优惠券ID
     */
    private Long couponId;

    /**
     * 优惠券抵扣金额
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal couponAmount;

    /**
     * 积分抵扣金额
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal pointAmount;

    /**
     * 使用的积分数量
     */
    private Integer usedPoints;


    /* ========== 支付信息 ========== */
    /**
     * 支付方式：1-微信支付 2-支付宝 3-银行卡 4-余额支付 5-货到付款
     */
    private Integer payMethod;

    /**
     * 第三方支付交易号（微信/支付宝交易号）
     */
    private String transactionId;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付截止时间（用于待付款订单）
     */
    private LocalDateTime payExpireTime;


    /* ========== 物流与收货信息 ========== */

    /**
     * 收货地址ID
     */
    private Long addressId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 完整收货地址
     */
    private String receiverAddress;

    /**
     * 期望配送时间
     */
    private LocalDateTime expectedDeliveryTime;

    /**
     * 物流公司编码
     */
    private String logisticsCompany;

    /**
     * 物流单号
     */
    private String trackingNo;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 实际送达时间
     */
    private LocalDateTime actualDeliveryTime;


    /* ========== 时间戳与审计信息 ========== */

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 订单完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 订单取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消类型：1-用户取消 2-系统自动取消 3-客服取消
     */
    private Integer cancelType;

    /**
     * 自动取消任务ID（用于延迟消息）
     */
    private String autoCancelTaskId;


    /* ========== 关联关系（一对多）========== */

    /**
     * 订单商品明细列表
     */
    @TableField(exist = false)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 订单操作日志
     */
    @TableField(exist = false)
    @Builder.Default
    private List<OrderLog> orderLogs = new ArrayList<>();


    /* ========== 发票信息 ========== */

    /**
     * 是否需要发票：0-否 1-是
     */
    private Boolean needInvoice;

    /**
     * 发票类型：1-个人 2-企业
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 纳税人识别号（企业发票需要）
     */
    private String taxpayerId;

    /**
     * 发票内容
     */
    private String invoiceContent;

    /**
     * 发票状态：0-未开票 1-已开票 2-已寄送
     */
    private Integer invoiceStatus;


    /* ========== 促销活动信息 ========== */

    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 促销活动名称
     */
    private String promotionName;

    /**
     * 满减活动ID
     */
    private Long fullDiscountId;


    /* ========== 风控与扩展信息 ========== */

    /**
     * 设备指纹（用于风控）
     */
    private String deviceFingerprint;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 用户客户端信息
     */
    private String userAgent;

    /**
     * 扩展字段（JSON格式，用于存储动态属性）
     */
    private String extData;

    /**
     * 数据版本（乐观锁）
     */
    @Version
    private Integer version;

    /**
     * 是否删除（0=非逻辑删除，1=逻辑删除）
     */
    private Integer deleted;
}
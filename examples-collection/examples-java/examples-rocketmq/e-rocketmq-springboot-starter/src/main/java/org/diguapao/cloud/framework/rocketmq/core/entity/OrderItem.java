package org.diguapao.cloud.framework.rocketmq.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品明细实体
 *
 * @author diguapao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单明细主键ID
     * SQLite 的 JDBC 驱动（如 org.xerial:sqlite-jdbc）不支持获取自增主键（Generated Keys）功能，因此设置 type 为 IdType.NONE 或 IdType.ASSIGN_ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long itemId;

    /**
     * 关联的订单ID
     */
    private Long orderId;

    /**
     * 订单编号（冗余字段，便于直接查询）
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品SKU ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品规格（如：颜色:白色,尺寸:XL）
     */
    private String specifications;

    /**
     * 商品单价
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal unitPrice;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 商品总价（单价×数量）
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalPrice;

    /**
     * 商品折扣金额
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal discountAmount;

    /**
     * 商品实付金额
     */
    @Digits(integer = 10, fraction = 2)
    private BigDecimal actualAmount;

    /**
     * 售后状态：0-无售后 1-售后中 2-售后完成
     */
    private Integer afterSaleStatus;

    /**
     * 是否评价：0-未评价 1-已评价
     */
    private Boolean commented = false;
    /**
     * 是否删除（0=非逻辑删除，1=逻辑删除）
     */
    private Integer deleted;
}
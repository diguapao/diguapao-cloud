package org.diguapao.cloud.framework.rocketmq.enums;

import lombok.Getter;

/**
 * 支付方式：1-微信支付 2-支付宝 3-银行卡 4-余额支付 5-货到付款
 *
 * @author diguapao
 * @version 2025.0.1
 * @since 2025/12/26 16:19
 */
@Getter
public enum PayMethod {
    WECHAT_PAY(1, "微信支付"),
    ALIPAY(2, "支付宝"),
    BANK_CARD(3, "银行卡"),
    BALANCE(4, "余额支付"),
    CASH_ON_DELIVERY(5, "货到付款");

    private final Integer code;
    private final String desc;

    PayMethod(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
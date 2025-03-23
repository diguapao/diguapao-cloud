package org.diguapao.cloud.trade.service.order;

import org.diguapao.cloud.trade.das.mysql.po.OrderPO;

public interface TradeService {
    String createOrder(OrderPO order);
}
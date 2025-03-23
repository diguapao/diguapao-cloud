package org.diguapao.cloud.trade.controller.order;

import org.apache.dubbo.config.annotation.DubboReference;
import org.diguapao.cloud.trade.das.mysql.po.OrderPO;
import org.diguapao.cloud.trade.service.order.TradeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class TradeController {

    @DubboReference(version = "1.0.0")
    private TradeService tradeService;

    @PostMapping
    public String createOrder(@RequestBody OrderPO order) {
        return tradeService.createOrder(order);
    }

}
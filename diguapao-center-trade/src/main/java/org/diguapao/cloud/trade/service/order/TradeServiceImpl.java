package org.diguapao.cloud.trade.service.order;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.diguapao.cloud.inventory.service.stock.InventoryService;
import org.diguapao.cloud.product.das.mysql.product.po.ProductPO;
import org.diguapao.cloud.product.service.product.ProductService;
import org.diguapao.cloud.trade.das.mysql.po.OrderPO;

@DubboService(version = "1.0.0")
public class TradeServiceImpl implements TradeService {

    @DubboReference(version = "1.0.0")
    private ProductService productService;

    @DubboReference(version = "1.0.0")
    private InventoryService inventoryService;

    @Override
    public String createOrder(OrderPO order) {
        // 获取产品信息
        ProductPO product = productService.getProductById(order.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // 扣减库存
        boolean success = inventoryService.reduceStock(order.getProductId(), order.getQuantity());
        if (!success) {
            throw new RuntimeException("Insufficient stock");
        }

        // 生成订单ID
        String orderId = "ORDER_" + System.currentTimeMillis();
        order.setOrderId(orderId);

        // 模拟保存订单到数据库
        System.out.println("Creating order: " + order);

        return orderId;
    }

}

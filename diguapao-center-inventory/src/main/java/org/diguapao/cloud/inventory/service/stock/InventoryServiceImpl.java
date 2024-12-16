package org.diguapao.cloud.inventory.service.stock;

import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.Map;

@DubboService(version = "1.0.0")
public class InventoryServiceImpl implements InventoryService {

    private Map<String, Integer> stockMap = new HashMap<>();

    {
        // 初始化库存数据
        stockMap.put("PRODUCT_001", 100);
        stockMap.put("PRODUCT_002", 200);
    }

    @Override
    public boolean reduceStock(String productId, int quantity) {
        if (stockMap.containsKey(productId)) {
            int currentStock = stockMap.get(productId);
            if (currentStock >= quantity) {
                stockMap.put(productId, currentStock - quantity);
                return true;
            }
        }
        return false;
    }
}
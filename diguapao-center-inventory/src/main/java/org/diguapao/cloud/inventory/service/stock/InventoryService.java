package org.diguapao.cloud.inventory.service.stock;

public interface InventoryService {
    boolean reduceStock(String productId, int quantity);
}
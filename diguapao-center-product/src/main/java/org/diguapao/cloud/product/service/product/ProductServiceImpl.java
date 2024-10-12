package org.diguapao.cloud.product.service.product;

import org.apache.dubbo.config.annotation.DubboService;
import org.diguapao.cloud.product.das.mysql.product.po.ProductPO;

@DubboService(version = "1.0.0")
public class ProductServiceImpl implements ProductService {

    @Override
    public ProductPO getProductById(String productId) {
        // 模拟从数据库获取产品信息
        ProductPO product = new ProductPO();
        product.setProductId(productId);
        product.setProductName("Product " + productId);
        product.setStock(100); // 假设初始库存为100
        return product;
    }
}
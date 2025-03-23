
package org.diguapao.cloud.product.service.product;


import org.diguapao.cloud.product.das.mysql.product.po.ProductPO;

public interface ProductService {
    ProductPO getProductById(String productId);
}
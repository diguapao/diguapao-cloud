package org.diguapao.cloud.product.controller.product;

import org.apache.dubbo.config.annotation.DubboReference;
import org.diguapao.cloud.product.das.mysql.product.po.ProductPO;
import org.diguapao.cloud.product.service.product.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @DubboReference(version = "1.0.0")
    private ProductService productService;

    @GetMapping("/{productId}")
    public ProductPO getProduct(@PathVariable String productId) {
        return productService.getProductById(productId);
    }
}
package org.diguapao.cloud.product.das.mysql.product.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private int stock;

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
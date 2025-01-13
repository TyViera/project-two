package com.travelport.projecttwo.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SaleHistoryResponse {

    private String id;
    private List<ProductInfo> products;
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class ProductInfo {
        private String productId;
        private String name;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
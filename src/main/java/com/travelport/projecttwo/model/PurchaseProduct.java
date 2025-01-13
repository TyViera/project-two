package com.travelport.projecttwo.model;

public class PurchaseProduct {

    private String productId;

    private Integer quantity;

    public PurchaseProduct(String productId, Integer quantity) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

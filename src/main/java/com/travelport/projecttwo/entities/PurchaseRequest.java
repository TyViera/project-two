package com.travelport.projecttwo.entities;

import java.util.UUID;

public class PurchaseRequest {
    private ProductRequest product;
    private String supplier;
    private int quantity;

    public ProductRequest getProduct() {
        return product;
    }

    public void setProduct(ProductRequest product) {
        this.product = product;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class ProductRequest {
        private String id;

        public UUID getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

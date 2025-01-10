package com.travelport.projecttwo.dto;

import java.util.List;

public class PurchaseRequest {
    private String supplier;
    private List<PurchasedProduct> products;

    public PurchaseRequest(String supplier, List<PurchasedProduct> products) {
        this.supplier = supplier;
        this.products = products;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<PurchasedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PurchasedProduct> products) {
        this.products = products;
    }

    public static class PurchasedProduct {
        private String id;
        private Integer quantity;

        public PurchasedProduct(String id, Integer quantity) {
            this.id = id;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}

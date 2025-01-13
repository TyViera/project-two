package com.travelport.projecttwo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema
public class ProductReportResponse {

    private Product product;
    private BigDecimal quantity;

    public ProductReportResponse(String id, String name, BigDecimal quantity) {
        this.product = new Product(id, name);
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public static class Product {
        private String id;
        private String name;

        public Product(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

}

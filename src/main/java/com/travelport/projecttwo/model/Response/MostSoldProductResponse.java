package com.travelport.projecttwo.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;

public class MostSoldProductResponse {

    @Schema(description = "Product information")
    private ProductInfo product;

    @Schema(description = "Total quantity sold")
    private long quantity;

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public static class ProductInfo {

        @Schema(description = "Product ID", example = "123e4567-e89b-12d3-a456-426614174000")
        private String id;

        @Schema(description = "Product name", example = "Product A")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

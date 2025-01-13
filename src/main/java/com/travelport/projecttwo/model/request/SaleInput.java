package com.travelport.projecttwo.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Schema(description = "Input for creating a sale")
public class SaleInput {

    @NotNull
    @Schema(description = "Product information")
    private ProductRef product;

    @NotNull
    @Schema(description = "Client information")
    private ClientRef client;

    @Positive
    @Schema(description = "Quantity to sell", example = "5")
    private int quantity;

    public ProductRef getProduct() {
        return product;
    }

    public void setProduct(ProductRef product) {
        this.product = product;
    }

    public ClientRef getClient() {
        return client;
    }

    public void setClient(ClientRef client) {
        this.client = client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class ProductRef {
        @NotNull
        @Schema(description = "Product ID", example = "123e4567-e89b-12d3-a456-426614174000")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ClientRef {
        @NotNull
        @Schema(description = "Client ID", example = "789e4567-e89b-12d3-a456-426614174001")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
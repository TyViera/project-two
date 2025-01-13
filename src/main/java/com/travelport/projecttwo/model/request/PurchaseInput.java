package com.travelport.projecttwo.model.request;

import com.travelport.projecttwo.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Schema(description = "Input for creating a purchase")
public class PurchaseInput {

    @Schema(description = "Product information")
    private Product product;

    @NotNull
    @Schema(description = "Supplier name", example = "Best Supplier Inc.")
    private String supplier;

    @Positive
    @Schema(description = "Quantity to purchase", example = "50")
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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
}

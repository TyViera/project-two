package com.travelport.projecttwo.controllers.dtos.purchase;

import com.travelport.projecttwo.controllers.dtos.product.ProductSaleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class PurchaseRequestDto {

    @NotNull
    private ProductSaleDto product;

    @NotBlank
    @Length(min = 2, max = 50)
    private String supplier;

    @Positive
    private int quantity;

    public ProductSaleDto getProduct() {
        return product;
    }

    public void setProduct(ProductSaleDto product) {
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
}

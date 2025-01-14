package com.travelport.projecttwo.controllers.dtos.purchase;

import com.travelport.projecttwo.controllers.dtos.product.ProductSaleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class PurchaseRequestDto {

    @NotBlank
    @Length(min = 2, max = 50)
    private String supplier;

    @NotNull
    private List<ProductSaleDto> products;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<ProductSaleDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSaleDto> products) {
        this.products = products;
    }
}

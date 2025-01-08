package com.travelport.projecttwo.controllers.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductSaleDto {

    @NotBlank
    @Size(min = 36, max = 36)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

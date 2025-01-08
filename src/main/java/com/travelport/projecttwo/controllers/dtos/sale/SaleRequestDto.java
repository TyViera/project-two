package com.travelport.projecttwo.controllers.dtos.sale;

import com.travelport.projecttwo.controllers.dtos.client.ClientSaleDto;
import com.travelport.projecttwo.controllers.dtos.product.ProductSaleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class SaleRequestDto {

    @NotBlank
    @Length(min = 36, max = 36)
    private ProductSaleDto product;

    @NotBlank
    @Length(min = 36, max = 36)
    private ClientSaleDto client;

    @NotBlank
    @Positive
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

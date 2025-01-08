package com.travelport.projecttwo.controllers.dtos.sale;

import com.travelport.projecttwo.controllers.dtos.client.ClientSaleDto;
import com.travelport.projecttwo.controllers.dtos.product.ProductSaleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SaleRequestDto {

    @NotNull
    private ProductSaleDto product;

    @NotNull
    private ClientSaleDto client;

    @Positive
    private int quantity;

    public ProductSaleDto getProduct() {
        return product;
    }

    public void setProduct(ProductSaleDto product) {
        this.product = product;
    }

    public ClientSaleDto getClient() {
        return client;
    }

    public void setClient(ClientSaleDto client) {
        this.client = client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

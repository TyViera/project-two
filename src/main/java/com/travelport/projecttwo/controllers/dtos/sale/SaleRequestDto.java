package com.travelport.projecttwo.controllers.dtos.sale;

import com.travelport.projecttwo.controllers.dtos.client.ClientSaleDto;
import com.travelport.projecttwo.controllers.dtos.product.ProductSaleDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class SaleRequestDto {

    @NotNull
    private ClientSaleDto client;

    @NotNull
    private List<ProductSaleDto> products;

    public ClientSaleDto getClient() {
        return client;
    }

    public void setClient(ClientSaleDto client) {
        this.client = client;
    }

    public List<ProductSaleDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSaleDto> products) {
        this.products = products;
    }
}

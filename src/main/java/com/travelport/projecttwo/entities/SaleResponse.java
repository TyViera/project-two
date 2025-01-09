package com.travelport.projecttwo.entities;

import java.util.List;
import java.util.UUID;

public class SaleResponse {
    private UUID id;
    private List<ProductSaleResponse> products;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ProductSaleResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSaleResponse> products) {
        this.products = products;
    }
}

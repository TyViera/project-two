package com.travelport.projecttwo.controllers.dtos.past_sales;

import java.util.List;

public class ClientPastSalesDto {

    private String id;
    private List<ProductsBoughtByClientDto> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductsBoughtByClientDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBoughtByClientDto> products) {
        this.products = products;
    }
}

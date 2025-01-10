package com.travelport.projecttwo.controllers.dtos.sale;

public class MostSoldProductsDto {

    private ProductInMostSoldProducts product;
    private int quantity;

    public ProductInMostSoldProducts getProduct() {
        return product;
    }

    public void setProduct(ProductInMostSoldProducts product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

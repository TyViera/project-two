package com.travelport.projecttwo.controllers.dtos.past_sales;

public class ProductsBoughtByClientDto {

    private ProductInPastSalesDto product;
    private int quantity;

    public ProductsBoughtByClientDto(ProductInPastSalesDto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductInPastSalesDto getProduct() {
        return product;
    }

    public void setProduct(ProductInPastSalesDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.travelport.projecttwo.controllers.dtos.past_sales;

public class ProductInPastSalesDto {

    private String id;
    private String name;

    public ProductInPastSalesDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

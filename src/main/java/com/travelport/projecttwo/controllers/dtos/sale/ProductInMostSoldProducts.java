package com.travelport.projecttwo.controllers.dtos.sale;

public class ProductInMostSoldProducts {

    private String id;
    private String name;

    public ProductInMostSoldProducts(String id, String name) {
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

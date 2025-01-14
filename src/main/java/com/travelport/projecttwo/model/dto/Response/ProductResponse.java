package com.travelport.projecttwo.model.dto.Response;

import java.util.UUID;

public class ProductResponse {
    private UUID id;
    private String productName;
    private Integer quantity;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}

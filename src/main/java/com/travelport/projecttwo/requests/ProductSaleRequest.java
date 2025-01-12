package com.travelport.projecttwo.requests;

import java.util.UUID;

public class ProductSaleRequest {

  private UUID id;
  private int quantity;

  public ProductSaleRequest() {}

  public ProductSaleRequest(UUID id, int quantity) {
    this.id = id;
    this.quantity = quantity;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}

package com.travelport.projecttwo.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ProductPurchaseRequest {

  @NotNull(message = "Product ID is required")
  private UUID id;

  @Min(value = 1, message = "Quantity must be a positive integer")
  private int quantity;

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

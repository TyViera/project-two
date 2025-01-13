package com.travelport.projecttwo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductIdQuantity {

  @NotNull
  private String id;

  @NotNull
  @Min(1)
  private Integer quantity;

  public @NotNull String getId() {
    return id;
  }

  public void setId(@NotNull String id) {
    this.id = id;
  }

  public @NotNull @Min(1) Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(@NotNull @Min(1) Integer quantity) {
    this.quantity = quantity;
  }

}

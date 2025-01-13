package com.travelport.projecttwo.controller.model;

import com.travelport.projecttwo.dto.ProductIdQuantity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SaleRequest {

  @NotNull
  private Client client;

  @NotNull
  private List<ProductIdQuantity> products;

  public static class Client {
    @NotNull
    private String id;

    public @NotNull String getId() {
      return id;
    }

    public void setId(@NotNull String id) {
      this.id = id;
    }
  }

  public @NotNull Client getClient() {
    return client;
  }

  public void setClient(@NotNull Client client) {
    this.client = client;
  }

  public @NotNull List<ProductIdQuantity> getProducts() {
    return products;
  }

  public void setProducts(@NotNull List<ProductIdQuantity> products) {
    this.products = products;
  }

}

package com.travelport.projecttwo.controller.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PurchaseRequest {

  @NotNull
  private String supplier;

  @NotNull
  private List<Product> products;

  public static class Product {
    @NotNull
    private String id;

    @NotNull
    @Min(1)
    private int quantity;

    public @NotNull String getId() {
      return id;
    }

    public void setId(@NotNull String id) {
      this.id = id;
    }

    @NotNull
    @Min(1)
    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(@NotNull @Min(1) int quantity) {
      this.quantity = quantity;
    }
  }

  public @NotNull String getSupplier() {
    return supplier;
  }

  public void setSupplier(@NotNull String supplier) {
    this.supplier = supplier;
  }

  public @NotNull List<Product> getProducts() {
    return products;
  }

  public void setProducts(@NotNull List<Product> products) {
    this.products = products;
  }

}

package com.travelport.projecttwo.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import jakarta.validation.Valid;

public class PurchaseRequest {

  @NotNull(message = "Supplier name is required")
  private String supplier;

  @NotEmpty(message = "At least one product purchase is required")
  private List<@Valid ProductPurchaseRequest> products;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public List<ProductPurchaseRequest> getProducts() {
    return products;
  }

  public void setProducts(List<ProductPurchaseRequest> products) {
    this.products = products;
  }
}

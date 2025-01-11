package com.travelport.projecttwo.requests;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.ProductSale;

public class SaleRequest {
  private UUID clientId;
  private List<ProductSale> productSales;

  public UUID getClientId() {
    return clientId;
  }

  public void setClientId(UUID clientId) {
    this.clientId = clientId;
  }

  public List<ProductSale> getProductSales() {
    return productSales;
  }

  public void setProductSales(List<ProductSale> productSales) {
    this.productSales = productSales;
  }
}
package com.travelport.projecttwo.requests;

import java.util.List;

public class SaleRequest {
  private ClientRequest client;
  private List<ProductSaleRequest> products;

  public ClientRequest getClient() {
    return client;
  }

  public void setClient(ClientRequest client) {
    this.client = client;
  }

  public List<ProductSaleRequest> getProducts() {
    return products;
  }

  public void setProducts(List<ProductSaleRequest> products) {
    this.products = products;
  }
}

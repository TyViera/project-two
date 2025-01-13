package com.travelport.projecttwo.service.model;

import com.travelport.projecttwo.dto.ProductDetails;

import java.util.List;

public class PastSaleResponse {

  private String id;
  private List<ProductDetails> products;

  public PastSaleResponse(String id, List<ProductDetails> products) {
    this.id = id;
    this.products = products;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<ProductDetails> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDetails> products) {
    this.products = products;
  }

}

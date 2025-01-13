package com.travelport.projecttwo.service.model;

import com.travelport.projecttwo.repository.entity.ProductEntity;

public class IncomeReportResponse {

  private ProductEntity product;

  private Integer quantity;

  public IncomeReportResponse() {}

  public IncomeReportResponse(ProductEntity product, Integer quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}

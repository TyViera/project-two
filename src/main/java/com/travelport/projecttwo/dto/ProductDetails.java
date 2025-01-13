package com.travelport.projecttwo.dto;

public class ProductDetails {

  private ProductIdName product;
  private Integer quantity;

  public ProductDetails(ProductIdName product, Integer quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public ProductIdName getProduct() {
    return product;
  }

  public void setProduct(ProductIdName product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

}

package com.travelport.projecttwo.exception;

public class ProductNotFoundException extends SaleException {

  public ProductNotFoundException(String id) {
    super("Product with id " + id + " not found");
  }

}

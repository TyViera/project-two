package com.travelport.projecttwo.exception;

public class NotEnoughStockException extends SaleException {

  public NotEnoughStockException(String productId) {
    super("Not enough stock for product with id " + productId);
  }
}

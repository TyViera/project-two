package com.travelport.projecttwo.responses;

public class StockResponse {
  private int stock;

  public StockResponse(int stock) {
    this.stock = stock;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
public class ProductStock {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private int quantity;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "ProductStock [id=" + id + ", product=" + product + ", quantity=" + quantity + "]";
  }
}

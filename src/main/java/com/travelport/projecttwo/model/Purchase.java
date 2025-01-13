package com.travelport.projecttwo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "purchases")
public class Purchase {

  @Id
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private String supplier;

  @Column(nullable = false)
  private int quantity;

  public Purchase() {
    if (this.id == null) {
      this.id = UUID.randomUUID().toString();
    }
  }

  public String getId() {
    return id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}

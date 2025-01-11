package com.travelport.projecttwo.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "product_purchases")
public class ProductPurchase {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "purchase_id", nullable = false)
  private Purchase purchase;

  @Column(nullable = false)
  private int quantity;

  public ProductPurchase() {}

  public ProductPurchase(Product product, Purchase purchase, int quantity) {
    this.product = product;
    this.purchase = purchase;
    this.quantity = quantity;
  }

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

  public Purchase getPurchase() {
    return purchase;
  }

  public void setPurchase(Purchase purchase) {
    this.purchase = purchase;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}

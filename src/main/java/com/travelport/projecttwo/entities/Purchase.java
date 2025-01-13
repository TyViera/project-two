package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchases")
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String supplier;

  @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
  private List<ProductPurchase> products;

  public Purchase() {}

  public Purchase(String supplier, List<ProductPurchase> products) {
    this.supplier = supplier;
    this.products = products;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public List<ProductPurchase> getProducts() {
    return products;
  }

  public void setProducts(List<ProductPurchase> products) {
    this.products = products;
  }
}

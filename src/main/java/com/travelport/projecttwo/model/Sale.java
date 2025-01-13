package com.travelport.projecttwo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sales")
public class Sale {

  @Id
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne(optional = false)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Column(nullable = false)
  private int quantity;

  public Sale() {
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

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}

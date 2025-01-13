package com.travelport.projecttwo.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales")
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
  private List<ProductSale> products;

  public Sale() {
  }

  public Sale(Client client, List<ProductSale> products) {
    this.client = client;
    this.products = products;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<ProductSale> getProducts() {
    return products;
  }

  public void setProducts(List<ProductSale> products) {
    this.products = products;
  }
}

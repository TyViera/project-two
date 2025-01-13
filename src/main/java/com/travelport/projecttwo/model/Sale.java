package com.travelport.projecttwo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Sale {

    @Id
    private String id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Client client;

    private int quantity;

    public Sale() {
        this.id = UUID.randomUUID().toString();
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
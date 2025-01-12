package com.travelport.projecttwo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales")
public class SaleEntity {

    @Id
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "product_id")
    private String productId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

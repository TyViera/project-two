package com.travelport.projecttwo.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class PurchaseProductEntity {

    @EmbeddedId
    private PurchaseProductId purchaseProductId;

    private Integer quantity;

    public PurchaseProductEntity(PurchaseProductId purchaseProductId, Integer quantity) {
        this.purchaseProductId = purchaseProductId;
        this.quantity = quantity;
    }

    public PurchaseProductEntity() {}

    public PurchaseProductId getPurchaseProductId() {
        return purchaseProductId;
    }

    public void setPurchaseProductId(PurchaseProductId purchaseProductId) {
        this.purchaseProductId = purchaseProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


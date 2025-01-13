package com.travelport.projecttwo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PurchaseProductId {

    @Column(name = "purchase_id")
    private String purchaseId;

    @Column(name = "product_id")
    private String productId;

    public PurchaseProductId(String purchaseId, String productId) {
        this.purchaseId = purchaseId;
        this.productId = productId;
    }

    public PurchaseProductId() {}

    public String getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

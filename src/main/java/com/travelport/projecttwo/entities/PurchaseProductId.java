package com.travelport.projecttwo.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PurchaseProductId {

    private String purchase_id;

    private String product_id;

    public PurchaseProductId(String purchase_id, String product_id) {
        this.purchase_id = purchase_id;
        this.product_id = product_id;
    }

    public PurchaseProductId() {}

    public String getPurchase_id() {
        return purchase_id;
    }
    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}

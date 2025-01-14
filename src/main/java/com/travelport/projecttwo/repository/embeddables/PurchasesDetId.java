package com.travelport.projecttwo.repository.embeddables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchasesDetId implements Serializable {

    private String purchaseId;
    private String productId;

    public PurchasesDetId(String purchaseId, String productId) {
        this.purchaseId = purchaseId;
        this.productId = productId;
    }

    public PurchasesDetId() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PurchasesDetId that)) return false;
        return Objects.equals(purchaseId, that.purchaseId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, productId);
    }
}

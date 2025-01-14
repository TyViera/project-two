package com.travelport.projecttwo.repository.embeddables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SalesDetId implements Serializable {

    private String saleId;
    private String productId;

    public SalesDetId(String saleId, String productId) {
        this.saleId = saleId;
        this.productId = productId;
    }

    public SalesDetId() {

    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SalesDetId that)) return false;
        return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productId);
    }
}

package com.travelport.projecttwo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class SaleProductId {

    @Column(name = "sale_id")
    private String saleId;

    @Column(name = "product_id")
    private String productId;

    public SaleProductId() {
    }

    public SaleProductId(String saleId, String productId) {
        this.saleId = saleId;
        this.productId = productId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProductId that = (SaleProductId) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productId);
    }
}

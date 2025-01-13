package com.travelport.projecttwo.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sales_products")
public class SaleProductEntity {

    @EmbeddedId
    private SaleProductId id;

    private int quantity;

    public SaleProductEntity() {}

    public SaleProductEntity(SaleProductId saleProductId, int quantity) {
        this.id = saleProductId;
        this.quantity = quantity;
    }

    public SaleProductId getId() {
        return id;
    }

    public void setId(SaleProductId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


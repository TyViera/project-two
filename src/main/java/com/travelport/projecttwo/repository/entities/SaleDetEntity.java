package com.travelport.projecttwo.repository.entities;

import com.travelport.projecttwo.repository.embeddables.SalesDetId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "sales_det")
public class SaleDetEntity {

    @EmbeddedId
    private SalesDetId id;

    @Column(nullable = false)
    @Positive
    private int quantity;

    @MapsId("saleId")
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private SaleCabEntity saleCab;

    public SalesDetId getId() {
        return id;
    }

    public void setId(SalesDetId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SaleCabEntity getSaleCab() {
        return saleCab;
    }

    public void setSaleCab(SaleCabEntity saleCab) {
        this.saleCab = saleCab;
    }
}

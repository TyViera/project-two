package com.travelport.projecttwo.repository.entities;

import com.travelport.projecttwo.repository.embeddables.PurchasesDetId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

@Entity
@Table(name = "purchases_det")
public class PurchaseDetEntity {

    @EmbeddedId
    private PurchasesDetId id;

    @Column(nullable = false)
    @Positive
    private int quantity;

    @MapsId("purchaseId")
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseCabEntity purchaseCab;

    public PurchasesDetId getId() {
        return id;
    }

    public void setId(PurchasesDetId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PurchaseCabEntity getPurchaseCab() {
        return purchaseCab;
    }

    public void setPurchaseCab(PurchaseCabEntity purchaseCab) {
        this.purchaseCab = purchaseCab;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PurchaseDetEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

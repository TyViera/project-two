package com.travelport.projecttwo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {
    @Id
    private String id;
    private String supplier;

    public PurchaseEntity(String id, String supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public PurchaseEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}

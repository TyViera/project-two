package com.travelport.projecttwo.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Schema
@Entity
public class Purchase {

    @Id
    @Schema( description = "Purchase ID")
    private UUID purchaseId = UUID.randomUUID();

    private String supplier;

    @OneToMany(mappedBy = "purchaseId")
    private List<PurchaseDetail> products;

    public UUID getPurchaseId() { return purchaseId; }
    public void setPurchaseId(UUID purchaseId) { this.purchaseId = purchaseId; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public List<PurchaseDetail> getProducts() { return products; }
    public void setProducts(List<PurchaseDetail> products) { this.products = products; }
}

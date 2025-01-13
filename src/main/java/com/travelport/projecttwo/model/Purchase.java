package com.travelport.projecttwo.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class Purchase {

    @NotNull
    private String id;

    @NotNull
    private String supplier;

    @NotNull
    private List<PurchaseProduct> products;

    public Purchase(@NotNull String supplier, @NotNull List<PurchaseProduct> products) {
        this.id = UUID.randomUUID().toString();
        this.supplier = supplier;
        this.products = products;
    }

    public Purchase(@NotNull String id, @NotNull String supplier, @NotNull List<PurchaseProduct> products) {
        this.id = id;
        this.supplier = supplier;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplier;
    }

    public void setSupplierName(String supplier) {
        this.supplier = supplier;
    }

    public List<PurchaseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PurchaseProduct> products) {
        this.products = products;
    }
}

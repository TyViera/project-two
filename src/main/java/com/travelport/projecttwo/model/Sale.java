package com.travelport.projecttwo.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class Sale {

    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private List<PurchaseProduct> products;

    public Sale(@NotNull String clientId, @NotNull List<PurchaseProduct> products) {
        this.id = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.products = products;
    }

    public Sale(@NotNull String id, @NotNull String clientId, @NotNull List<PurchaseProduct> products) {
        this.id = id;
        this.clientId = clientId;
        this.products = products;
    }

    public @NotNull String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public @NotNull String getClientId() {
        return clientId;
    }

    public void setClientId(@NotNull String clientId) {
        this.clientId = clientId;
    }

    public @NotNull List<PurchaseProduct> getProducts() {
        return products;
    }

    public void setProducts(@NotNull List<PurchaseProduct> products) {
        this.products = products;
    }
}

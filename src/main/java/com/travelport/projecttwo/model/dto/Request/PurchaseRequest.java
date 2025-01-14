package com.travelport.projecttwo.model.dto.Request;

import java.util.Map;
import java.util.UUID;

public class PurchaseRequest {
    String supplier;
    Map<UUID, Integer> products;

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public Map<UUID, Integer> getProducts() { return products; }
    public void setProducts(Map<UUID, Integer> products) { this.products = products; }
}

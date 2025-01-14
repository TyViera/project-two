package com.travelport.projecttwo.services.domainModels;

import java.util.List;

public class PurchaseCabDomain {

    private String id;
    private String supplier;
    private List<PurchaseDetDomain> details;

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

    public List<PurchaseDetDomain> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseDetDomain> details) {
        this.details = details;
    }
}

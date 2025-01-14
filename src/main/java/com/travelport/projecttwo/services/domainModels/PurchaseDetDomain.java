package com.travelport.projecttwo.services.domainModels;

public class PurchaseDetDomain {

    private String id;
    private int quantity;
    private PurchaseCabDomain purchaseCab;

    public PurchaseDetDomain(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PurchaseCabDomain getPurchaseCab() {
        return purchaseCab;
    }

    public void setPurchaseCab(PurchaseCabDomain purchaseCab) {
        this.purchaseCab = purchaseCab;
    }
}

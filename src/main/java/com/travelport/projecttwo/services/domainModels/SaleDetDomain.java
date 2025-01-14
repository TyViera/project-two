package com.travelport.projecttwo.services.domainModels;

import com.travelport.projecttwo.repository.embeddables.SalesDetId;

public class SaleDetDomain {

    private String id;
    private int quantity;
    private SaleCabDomain saleCab;

    public SaleDetDomain(String id, int quantity) {
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

    public SaleCabDomain getSaleCab() {
        return saleCab;
    }

    public void setSaleCab(SaleCabDomain saleCab) {
        this.saleCab = saleCab;
    }
}

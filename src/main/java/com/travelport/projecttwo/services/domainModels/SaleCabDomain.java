package com.travelport.projecttwo.services.domainModels;

import java.util.List;

public class SaleCabDomain {

    private String id;
    private String clientId;
    private List<SaleDetDomain> details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<SaleDetDomain> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetDomain> details) {
        this.details = details;
    }
}

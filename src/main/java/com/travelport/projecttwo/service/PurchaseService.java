package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.dto.Request.PurchaseRequest;

public interface PurchaseService {
    void createPurchase(PurchaseRequest purchaseRequest);
}

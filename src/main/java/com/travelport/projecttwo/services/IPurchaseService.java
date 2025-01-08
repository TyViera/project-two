package com.travelport.projecttwo.services;

import com.travelport.projecttwo.controllers.dtos.purchase.PurchaseRequestDto;

public interface IPurchaseService {

    void createPurchase(PurchaseRequestDto purchaseRequest);
}

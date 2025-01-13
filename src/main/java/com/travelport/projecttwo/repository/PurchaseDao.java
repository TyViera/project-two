package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.PurchaseEntity;
import com.travelport.projecttwo.entities.PurchaseProductEntity;

public interface PurchaseDao {
    void addPurchase(PurchaseEntity purchase);

    void addPurchaseProduct(PurchaseProductEntity purchaseProduct);
}

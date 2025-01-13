package com.travelport.projecttwo.services;

import java.util.List;

import com.travelport.projecttwo.entities.ProductPurchase;

public interface PurchaseService {
  void purchaseProduct(String supplier, List<ProductPurchase> productPurchases);
}

package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.entities.PurchaseEntity;
import com.travelport.projecttwo.entities.PurchaseProductEntity;
import com.travelport.projecttwo.entities.PurchaseProductId;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.PurchaseProduct;
import com.travelport.projecttwo.repository.PurchaseDao;
import com.travelport.projecttwo.services.PurchaseService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDao purchaseDao;

    public PurchaseServiceImpl(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    @Override
    public Purchase addPurchase(Purchase purchase) {
        // Add purchase to the database
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchase.getId());
        purchaseEntity.setSupplier(purchase.getSupplierName());
        purchaseDao.addPurchase(purchaseEntity);

        // Add purchased products to the database
        for (PurchaseProduct product : purchase.getProducts()) {
            PurchaseProductEntity purchaseProductEntity = new PurchaseProductEntity();
            PurchaseProductId purchaseProductId = new PurchaseProductId(purchase.getId(), product.getProductId());
            purchaseProductEntity.setPurchaseProductId(purchaseProductId);
            purchaseProductEntity.setQuantity(product.getQuantity());
            purchaseDao.addPurchaseProduct(purchaseProductEntity);
        }
        return purchase;
    }
}

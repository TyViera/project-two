package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.repository.entities.PurchaseEntity;
import com.travelport.projecttwo.services.domainModels.PurchaseDomain;

public class PurchaseMappings {

    public static PurchaseEntity toEntity(PurchaseDomain purchaseDomain) {
        var purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseDomain.getId());

        var productEntity = new ProductEntity();
        productEntity.setId(purchaseDomain.getProductId());
        purchaseEntity.setProduct(productEntity);

        purchaseEntity.setSupplierName(purchaseDomain.getSupplierName());
        purchaseEntity.setQuantity(purchaseDomain.getQuantity());

        return purchaseEntity;
    }
}

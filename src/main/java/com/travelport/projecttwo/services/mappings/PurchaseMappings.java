package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.controllers.dtos.purchase.PurchaseRequestDto;
import com.travelport.projecttwo.repository.embeddables.PurchasesDetId;
import com.travelport.projecttwo.repository.entities.PurchaseCabEntity;
import com.travelport.projecttwo.repository.entities.PurchaseDetEntity;
import com.travelport.projecttwo.services.domainModels.PurchaseCabDomain;
import com.travelport.projecttwo.services.domainModels.PurchaseDetDomain;

import java.util.List;

public class PurchaseMappings {

    public static PurchaseCabDomain toDomain(PurchaseRequestDto purchaseRequest) {
        var purchaseCabDomain = new PurchaseCabDomain();

        purchaseCabDomain.setSupplier(purchaseRequest.getSupplier());
        var details = purchaseRequest.getProducts().stream()
                .map(det -> new PurchaseDetDomain(det.getId(), det.getQuantity()))
                .toList();

        purchaseCabDomain.setDetails(details);

        return purchaseCabDomain;
    }

    public static PurchaseCabEntity toEntity(PurchaseCabDomain purchaseDomain) {
        var purchaseCabEntity = new PurchaseCabEntity();

        purchaseCabEntity.setId(purchaseDomain.getId());
        purchaseCabEntity.setSupplier(purchaseDomain.getSupplier());

        List<PurchaseDetEntity> detEntities = purchaseDomain.getDetails().stream().map(detDomain -> {
            var detEntity = new PurchaseDetEntity();
            detEntity.setId(new PurchasesDetId(purchaseDomain.getId(), detDomain.getId()));
            detEntity.setQuantity(detDomain.getQuantity());
            return detEntity;
        }).toList();

        purchaseCabEntity.setDetails(detEntities);

        detEntities.forEach(detEntity -> detEntity.setPurchaseCab(purchaseCabEntity));

        return purchaseCabEntity;
    }
}

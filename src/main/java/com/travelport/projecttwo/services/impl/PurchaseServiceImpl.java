package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.controllers.dtos.purchase.PurchaseRequestDto;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.IPurchasesCabRepository;
import com.travelport.projecttwo.services.IPurchaseService;
import com.travelport.projecttwo.services.domainModels.PurchaseDetDomain;
import com.travelport.projecttwo.services.mappings.PurchaseMappings;
import com.travelport.projecttwo.services.mappings.SaleMappings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final IProductRepository productRepository;
    private final IPurchasesCabRepository purchasesCabRepository;

    public PurchaseServiceImpl(IProductRepository productRepository, IPurchasesCabRepository purchasesCabRepository) {
        this.productRepository = productRepository;
        this.purchasesCabRepository = purchasesCabRepository;
    }

    @Override
    @Transactional
    public void createPurchase(PurchaseRequestDto purchaseRequest) {

        var purchaseCabDomain = PurchaseMappings.toDomain(purchaseRequest);

        for (PurchaseDetDomain detail : purchaseCabDomain.getDetails()) {
            var productOpt = productRepository.findById(detail.getId());
            if (productOpt.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }

            var product = productOpt.get();

            product.setStock(product.getStock() + detail.getQuantity());

            productRepository.save(product);
        }

        purchaseCabDomain.setId(UUID.randomUUID().toString());

        var purchaseCabEntity = PurchaseMappings.toEntity(purchaseCabDomain);

        purchasesCabRepository.save(purchaseCabEntity);

    }
}

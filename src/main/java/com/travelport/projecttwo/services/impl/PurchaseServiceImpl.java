package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.controllers.dtos.purchase.PurchaseRequestDto;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.IPurchaseRepository;
import com.travelport.projecttwo.services.IPurchaseService;
import com.travelport.projecttwo.services.domainModels.PurchaseDomain;
import com.travelport.projecttwo.services.mappings.PurchaseMappings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final IProductRepository productRepository;
    private final IPurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(IProductRepository productRepository, IPurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional
    public void createPurchase(PurchaseRequestDto purchaseRequest) {

        var product = productRepository.findById(purchaseRequest.getProduct().getId());

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        var purchase = new PurchaseDomain();
        purchase.setId(UUID.randomUUID().toString());
        purchase.setProductId(product.get().getId());
        purchase.setSupplierName(purchaseRequest.getSupplier());
        purchase.setQuantity(purchaseRequest.getQuantity());

        purchaseRepository.save(PurchaseMappings.toEntity(purchase));

        productRepository.updateStock(product.get().getId(), purchaseRequest.getQuantity());

    }
}

package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.dto.PurchaseRequest;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductRepository productRepository;

    public PurchaseServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void renewStock(PurchaseRequest purchaseRequest) {
        List<PurchaseRequest.PurchasedProduct> requestedProducts = purchaseRequest.getProducts();
        for (PurchaseRequest.PurchasedProduct p : requestedProducts) {
            if (productRepository.findById(p.getId()).isEmpty()) {
                throw new NoSuchElementException("Requested product doesn't exist");
            }

            if (p.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity to purchase");
            }
            Integer updatedProduct = productRepository.updateProductStock(p.getId(), p.getQuantity());
            if (updatedProduct <= 0) {
                throw new IllegalArgumentException("Product stock wasn't updated for product ID: " + p.getId());
            }

        }
    }
}

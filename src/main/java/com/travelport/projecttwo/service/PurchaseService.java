package com.travelport.projecttwo.service;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.Purchase;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    public Purchase save(Purchase purchase) {
        Optional<Product> productOptional = productRepository.findById(purchase.getProduct().getId());
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();

        product.setStock(product.getStock() + purchase.getQuantity());
        productRepository.save(product);

        return purchaseRepository.save(purchase);
    }
}

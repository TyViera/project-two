package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.entity.Purchase;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void renewStock(String productId, String supplier, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setSupplier(supplier);
        purchase.setQuantity(quantity);

        purchaseRepository.save(purchase);
    }
}

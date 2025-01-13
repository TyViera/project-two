package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.request.PurchaseInput;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void purchaseProduct(PurchaseInput purchaseInput) {

        Optional<Product> productOpt = productRepository.findById(purchaseInput.getProduct().getId());
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOpt.get();

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setSupplier(purchaseInput.getSupplier());
        purchase.setQuantity(purchaseInput.getQuantity());

        product.setStock(product.getStock() + purchaseInput.getQuantity());
        productRepository.save(product);

        purchaseRepository.save(purchase);
    }
}

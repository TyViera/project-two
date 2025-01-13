package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private ProductRepository productRepository;

  public Purchase createPurchase(@Valid Purchase purchase) {
    Product product = productRepository.findById(purchase.getProduct().getId())
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));

    int newStock = product.getStock() + purchase.getQuantity();
    product.setStock(newStock);
    productRepository.save(product);
    return purchaseRepository.save(purchase);
  }
}

package com.travelport.projecttwo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductPurchase;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.entities.Purchase;
import com.travelport.projecttwo.repositories.ProductRepository;
import com.travelport.projecttwo.repositories.ProductStockRepository;
import com.travelport.projecttwo.repositories.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService {

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductStockRepository productStockRepository;

  @Override
  public void purchaseProduct(String supplier, List<ProductPurchase> productPurchases) {
    Purchase purchase = new Purchase(supplier, productPurchases);

    for (ProductPurchase productPurchase : productPurchases) {
      Product product = productRepository.findById(productPurchase.getProduct().getId())
          .orElseThrow(() -> new RuntimeException("Product not found"));

      ProductStock productStock = productStockRepository.findByProductId(productPurchase.getProduct().getId())
          .orElse(new ProductStock());

      productStock.setProduct(product);
      productStock.setQuantity(productStock.getQuantity() + productPurchase.getQuantity());
      productStockRepository.save(productStock);

      productPurchase.setPurchase(purchase);
    }

    purchaseRepository.save(purchase);
  }
}

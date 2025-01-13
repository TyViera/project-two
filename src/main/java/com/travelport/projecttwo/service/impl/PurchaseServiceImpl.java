package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.PurchaseRequest;
import com.travelport.projecttwo.exception.ProductNotFoundException;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

  private final ProductRepository productRepository;

  public PurchaseServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  @Transactional
  public void renewStock (PurchaseRequest purchase) throws ProductNotFoundException {
    var products = purchase.getProducts();
    products.forEach(product -> {
      var isProductFound = productRepository.findById(product.getId());
      if (isProductFound.isEmpty()) throw new ProductNotFoundException(product.getId());

      var foundProduct = isProductFound.get();
      var newStock = foundProduct.getStock() + product.getQuantity();

      foundProduct.setStock(newStock);
      productRepository.save(foundProduct);
    });
  }

}

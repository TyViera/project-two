package com.travelport.projecttwo.services;

import java.util.UUID;

import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.repositories.ProductStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductStockServiceImpl implements ProductStockService {

  @Autowired
  private ProductStockRepository productStockRepository;

  public ProductStock create(ProductStock productStock) {
    return productStockRepository.save(productStock);
  }

  public ProductStock getStockByProductId(UUID productId) {
    return productStockRepository.findByProductId(productId)
        .orElse(null);
  }
}

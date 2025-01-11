package com.travelport.projecttwo.services;

import com.travelport.projecttwo.entities.ProductStock;
import java.util.UUID;

public interface ProductStockService {
  ProductStock create(ProductStock productStock);
  ProductStock getStockByProductId(UUID productId);
}

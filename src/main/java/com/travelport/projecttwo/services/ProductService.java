package com.travelport.projecttwo.services;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.Product;

public interface ProductService {
  Product create(Product product);
  Product read(UUID id);
  Product update(UUID id, Product product);
  void delete(UUID id);
  List<Product> getAllProducts();
}

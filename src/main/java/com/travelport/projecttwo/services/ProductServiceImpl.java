package com.travelport.projecttwo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Product create(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product read(UUID id) {
    return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
  }

  @Override
  public Product update(UUID id, Product product) {
    Product existingProduct = read(id);
    existingProduct.setName(product.getName());
    existingProduct.setCode(product.getCode());
    return productRepository.save(existingProduct);
  }

  @Override
  public void delete(UUID id) {
    productRepository.deleteById(id);
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
}

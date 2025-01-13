package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SaleRepository saleRepository;

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Optional<Product> getProductById(String id) {
    return productRepository.findById(id);
  }

  @Transactional
  public Product createProduct(Product product) {
    if (productRepository.existsByCode(product.getCode())) {
      return null;
    }
    product.setStock(0);
    return productRepository.save(product);
  }

  @Transactional
  public Product updateProduct(String id, Product updatedProduct) {
    Optional<Product> existingProduct = productRepository.findById(id);

    if (existingProduct.isEmpty()) {
      return null;
    }
    else {
      Product product = existingProduct.get();
      product.setCode(updatedProduct.getCode());
      product.setName(updatedProduct.getName());
      product.setStock(updatedProduct.getStock());

      return productRepository.save(product);
    }
  }

  @Transactional
  public boolean deleteProduct(String id) {
    Optional<Product> product = productRepository.findById(id);
    if (productRepository.findById(id).isEmpty()) {
      return false;
    }
    productRepository.deleteById(id);
    return true;
  }
}

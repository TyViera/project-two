package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByCode(product.getCode());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("There is already a product with the provided code");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setCode(productDetails.getCode());
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}

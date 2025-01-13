package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import com.travelport.projecttwo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("Product code must be unique");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setCode(product.getCode());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}

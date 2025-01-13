package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("There is already a product with the provided code");
        }
        product.setId(java.util.UUID.randomUUID().toString());
        product.setStock(0);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }

        productRepository.deleteById(id);
    }
}

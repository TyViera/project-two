package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleDetailRepository;
import com.travelport.projecttwo.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Autowired //TODO: check if this is always necesary
    public ProductServiceImpl(ProductRepository productRepository, SaleDetailRepository saleDetailRepository) {
        this.productRepository = productRepository;
        this.saleDetailRepository=saleDetailRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new EntityNotFoundException("Product not found");
        return product.get();
    }

    @Override
    public void deleteProduct(String id) {
        if(productRepository.findById(id).isEmpty()) throw new EntityNotFoundException("Product not found");
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Product exists on sale");
        }
    }

    @Override
    public Product addProduct(Product product) {
        Product newProduct = new Product(UUID.randomUUID().toString(), product.getName(), product.getCode());
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(String id, Product productData) {
        Product existingClient = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        existingClient.setName(productData.getName());
        existingClient.setCode(productData.getCode());
        return productRepository.save(existingClient);
    }

}

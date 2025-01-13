package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductById(String id);

    Product createProduct(Product product);

    Product updateProduct(String id, Product product);

    void deleteProduct(String id);
}
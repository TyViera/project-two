package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Product getProductById(String id);

    void deleteProduct(String id);

    Product addProduct(Product product);

    Product updateProduct(String id, Product product);

}

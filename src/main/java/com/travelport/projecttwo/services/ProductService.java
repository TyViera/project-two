package com.travelport.projecttwo.services;

import com.travelport.projecttwo.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductEntity> getProducts();

    Optional<ProductEntity> getProductById(String id);

    ProductEntity createProduct(ProductEntity product);

    Optional<ProductEntity> updateProduct(String id, ProductEntity product);

    void deleteProduct(String id) throws NullPointerException, IllegalArgumentException;
}

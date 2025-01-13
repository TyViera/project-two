package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<ProductEntity> getProducts();

    Optional<ProductEntity> getProduct(String id);

    void createProduct(ProductEntity product);

    Optional<ProductEntity> updateProduct(String id, ProductEntity product);

    int deleteProduct(String id);

    int getAssociatedSalesCount(String id);
}

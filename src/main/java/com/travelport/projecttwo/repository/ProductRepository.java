package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByCode(String code);
}

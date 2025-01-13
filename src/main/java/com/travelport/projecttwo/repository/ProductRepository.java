package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, String> {
  @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.code = ?1")
  boolean existsByCode(String code);
}

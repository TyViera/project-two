package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
  Optional<Product> findByCode(String code);
}

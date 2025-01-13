package com.travelport.projecttwo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

import com.travelport.projecttwo.entities.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, UUID> {
  Optional<ProductStock> findByProductId(UUID productId);
}

package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.ProductPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, UUID> {
  List<ProductPurchase> findByPurchaseId(UUID purchaseId);
}

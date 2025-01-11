package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
  List<Purchase> findBySupplier(String supplierName);
}

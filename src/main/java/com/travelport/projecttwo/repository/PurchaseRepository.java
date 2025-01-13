package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}

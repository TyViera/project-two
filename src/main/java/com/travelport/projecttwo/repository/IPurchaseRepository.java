package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseRepository extends JpaRepository<PurchaseEntity, String> {
}

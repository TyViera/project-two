package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.embeddables.PurchasesDetId;
import com.travelport.projecttwo.repository.entities.PurchaseDetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchasesDetRepository extends JpaRepository<PurchaseDetEntity, PurchasesDetId> {
}

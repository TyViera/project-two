package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.PurchaseCabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchasesCabRepository extends JpaRepository<PurchaseCabEntity, String> {
}

package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseJpaRespository extends JpaRepository <Purchase, UUID> {
}

package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.model.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseDetailJpaRepository extends JpaRepository<PurchaseDetail, UUID> {
}

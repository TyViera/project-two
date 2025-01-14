package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalesDetailJpaRepository extends JpaRepository<SaleDetail, UUID> {
    List<SaleDetail> findBySaleId(Sale sale);
}

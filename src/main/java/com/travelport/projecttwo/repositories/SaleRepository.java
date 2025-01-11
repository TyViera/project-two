package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
  List<Sale> findByClientId(UUID clientId);
}

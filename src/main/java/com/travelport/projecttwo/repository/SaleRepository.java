package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {
    List<Sale> findByClientId(String clientId);
}

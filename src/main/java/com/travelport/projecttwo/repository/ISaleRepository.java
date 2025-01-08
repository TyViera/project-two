package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<SaleEntity, String> {
}

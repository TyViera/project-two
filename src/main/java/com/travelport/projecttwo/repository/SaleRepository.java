package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, String> {

  public List<SaleEntity> findByClientId (String clientId);

}

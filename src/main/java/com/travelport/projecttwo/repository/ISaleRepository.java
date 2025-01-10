package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISaleRepository extends JpaRepository<SaleEntity, String> {

    @Query("SELECT s FROM SaleEntity s JOIN FETCH s.product p WHERE s.client.id = :clientId")
    List<SaleEntity> findAllByClientId(@Param("clientId") String clientId);
}

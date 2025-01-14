package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.SaleCabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISalesCabRepository extends JpaRepository<SaleCabEntity, String> {

    @Query("""
        SELECT DISTINCT sc
        FROM SaleCabEntity sc
        JOIN FETCH sc.details d
        WHERE sc.clientId = :clientId
    """)
    List<SaleCabEntity> findAllByClientId(@Param("clientId") String clientId);

    boolean existsByClientId(String clientId);
}

package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.embeddables.SalesDetId;
import com.travelport.projecttwo.repository.entities.SaleDetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISalesDetRepository extends JpaRepository<SaleDetEntity, SalesDetId> {

    @Query(value = """
        SELECT p.id, 
               p.name, 
               SUM(sd.quantity) as quantity
        FROM sales_det sd
        JOIN products p ON p.id = sd.product_id
        GROUP BY p.id, p.name
        ORDER BY quantity DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> findMostSoldProducts();

    boolean existsByIdProductId(String productId);
}

package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {

    @Query(value = """
        SELECT s.id
        FROM sales s
        WHERE s.client_id=:clientId
    """, nativeQuery = true)
    List<String> getSalesIdByClientId(@Param("clientId") String clientId);

    @Query(value = """
        SELECT s.id
        FROM sales s
        INNER JOIN sales_det sd ON s.id = sd.sale_id
        WHERE sd.product_id = :productId
        AND s.client_id = :clientId
    """, nativeQuery = true)
    List<String> getSalesDetIdByProductId(@Param("productId") String productId);
}

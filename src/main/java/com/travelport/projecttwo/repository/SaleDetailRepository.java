package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, String> {
    @Query(value = """
        SELECT *
        FROM sales_det sd
        WHERE sd.sale_id=:saleId
    """, nativeQuery = true)
    List<SaleDetail> findBySaleId(@Param("saleId") String saleId);

    @Query(value = """
        SELECT COUNT(*)
        FROM sales s
        INNER JOIN sales_det sd ON s.id = sd.sale_id
        WHERE sd.product_id = :productId
    """, nativeQuery = true)
    Long countSalesDetByProductId(@Param("productId") String productId);

}

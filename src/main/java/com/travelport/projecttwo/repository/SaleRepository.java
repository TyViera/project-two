package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {

  @Query("SELECT s.product.id, s.product.name, SUM(s.quantity) AS totalSold FROM Sale s GROUP BY s.product.id, s.product.name ORDER BY totalSold DESC")
  List<Object[]> findMostSoldProducts();

  @Query("SELECT s FROM Sale s WHERE s.client.id = :clientId")
  List<Sale> findSalesByClientId(@Param("clientId") String clientId);
}

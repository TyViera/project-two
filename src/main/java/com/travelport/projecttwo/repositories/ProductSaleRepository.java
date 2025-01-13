package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductSaleRepository extends JpaRepository<ProductSale, UUID> {

  @Query("SELECT ps FROM ProductSale ps GROUP BY ps.product ORDER BY SUM(ps.quantity) DESC")
  List<ProductSale> findTop5();
}

package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductSaleRepository extends JpaRepository<ProductSale, UUID> {
  List<ProductSale> findBySaleId(UUID saleId);
  @Query("SELECT ps FROM ProductSale ps ORDER BY ps.quantity DESC")
  public List<ProductSale> findTop5();

}

package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entity.ProductEntity;
import com.travelport.projecttwo.repository.entity.SaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetailEntity, String> {

  public List<SaleDetailEntity> findBySaleId (String saleId);

  @Query("SELECT s.product, SUM(s.quantity) FROM SaleDetailEntity s GROUP BY s.product ORDER BY SUM(s.quantity) DESC LIMIT 5")
  public List<ProductEntity> findMostSoldProducts();

  @Query("SELECT SUM(s.quantity) FROM SaleDetailEntity s WHERE s.product.id = :productId")
  public Integer getTimesSoldById (@Param("productId") String productId);

}

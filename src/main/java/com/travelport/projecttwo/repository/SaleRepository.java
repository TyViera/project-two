package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {

    @Query("SELECT s.product, SUM(s.quantity) as totalQuantity " +
            "FROM Sale s " +
            "GROUP BY s.product " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTop5MostSoldProducts();

    List<Sale> findByClientId(String clientId);
}

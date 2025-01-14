package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.model.Sale;
import org.hibernate.dialect.function.ListaggStringAggEmulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalesJpaRepository extends JpaRepository <Sale, UUID> {

    List<Sale> findByClientId(Client client);

    @Query(value = """
        SELECT s.sale_id, sd.product_id, sd.quantity, p.name
        FROM Sale s
        JOIN sale_detail sd ON s.sale_id = sd.sale_id
        JOIN product p ON sd.product_id = p.id
        WHERE s.client_id = :clientId
        ORDER BY s.sale_id DESC
    """, nativeQuery = true)
    List<Object[]> findLastSalesByClientId(@Param("clientId") UUID clientId);

    @Query(value = """
        SELECT sd.product_id AS productId,
               p.name AS productName,
               SUM(sd.quantity) AS quantity
        FROM sale_detail sd
        INNER JOIN product p ON sd.product_id = p.product_id
        GROUP BY sd.product_id, p.name
        ORDER BY SUM(sd.quantity) DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> findTop5MostSoldProducts();
}

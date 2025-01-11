package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM sales_det WHERE product_id = :productId", nativeQuery = true)
    boolean productHasSales(@Param("productId") String productId);


    @Query(value = """
        SELECT p.id AS id, p.name AS name, SUM(sd.quantity) AS quantity
        FROM sales_det sd
        JOIN sales s ON sd.sale_id = s.id
        JOIN products p ON p.id = sd.product_id
        GROUP BY p.id
        ORDER BY SUM(sd.quantity) DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> getMostSoldProducts();

    @Query(value = """
        SELECT *
        FROM products p
        JOIN sales_det sd ON p.id = sd.product_id
        WHERE sd.id=:saleId
        AND sd.product_id = :productId
    """, nativeQuery = true)
    Product getProductsBySaleId(@Param("saleId") String saleId, @Param("productId") String productId);

    @Modifying
    @Query(value = """
        UPDATE products p
        SET p.stock = p.stock + :quantity
        WHERE p.id = :productId
    """, nativeQuery = true)
    int updateProductStock(@Param("productId") String productId, @Param("quantity") Integer quantity);
}

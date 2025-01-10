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
    @Query(value = "SELECT COUNT(*) > 0 FROM sales_det WHERE product_id = :productId", nativeQuery = true)
    boolean existsByProductId(@Param("productId") String productId);

    @Query(value = """
        SELECT p.*
        FROM products p
        JOIN sales_det sd ON p.id = sd.product_id
        GROUP BY p.id, p.name, p.code, p.stock
        ORDER BY SUM(sd.quantity) DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Product> getMostSoldProducts();

    @Query(value = """
        SELECT *
        FROM products p
        JOIN sales_det sd ON p.id = sd.product_id
        WHERE sd.id=:saleId
    """, nativeQuery = true)
    List<Product> getProductsBySaleId(@Param("saleId") String saleId);

    @Modifying
    @Query(value = """
        UPDATE products p
        SET p.stock = p.stock + :quantity
        WHERE p.id = :productId
    """, nativeQuery = true)
    int updateProductStock(@Param("productId") String productId, @Param("quantity") Integer quantity);
}

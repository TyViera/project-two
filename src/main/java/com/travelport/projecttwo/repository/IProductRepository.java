package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<ProductEntity, String> {

    @Modifying
    @Query("UPDATE ProductEntity p SET p.stock = p.stock + :quantity WHERE p.id = :id")
    void updateStock(@Param("id") String id, @Param("quantity") int quantity);

    boolean existsByCode(String code);
}

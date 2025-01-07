package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, String> {

}

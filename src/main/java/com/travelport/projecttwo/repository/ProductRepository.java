package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

  public List<ProductEntity> findAllByOrderByCode ();

  public boolean existsByCode (String code);

}

package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository <Product, UUID> {}

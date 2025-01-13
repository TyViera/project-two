package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByCode(@NonNull String code);

    @Nullable
    Optional<Product> findById(@NonNull String id);
}
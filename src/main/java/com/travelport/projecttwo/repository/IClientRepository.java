package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.repository.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<ClientEntity, String> {

    boolean existsByNif(String nif);
}

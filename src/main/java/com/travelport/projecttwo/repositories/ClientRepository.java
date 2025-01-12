package com.travelport.projecttwo.repositories;

import com.travelport.projecttwo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
  Optional<Client> findByNif(String nif);
}

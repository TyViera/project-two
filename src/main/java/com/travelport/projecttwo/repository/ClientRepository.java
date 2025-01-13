package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
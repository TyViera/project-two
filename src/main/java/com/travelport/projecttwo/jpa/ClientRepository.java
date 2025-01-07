package com.travelport.projecttwo.jpa;

import com.travelport.projecttwo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}

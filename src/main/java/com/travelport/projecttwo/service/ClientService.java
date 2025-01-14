package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    List<Client> getAllClients();
    Optional<Client> getClientByID(UUID id);
    Client createClient(Client client);
    Optional<Client> updateClient(UUID id, Client client);
    void deleteClient(UUID id);
}

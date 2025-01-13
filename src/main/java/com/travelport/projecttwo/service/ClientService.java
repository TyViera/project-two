package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();

    Optional<Client> getClientById(String id);

    void deleteClient(String id);

    Client save(Client client);

    Client update(String id, Client client);
}
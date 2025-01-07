package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getClients();

    Client getClientById(String id);

    void deleteClient(String id);

    Client addClient(Client client);

    Client updateClient(String id, Client client);
}

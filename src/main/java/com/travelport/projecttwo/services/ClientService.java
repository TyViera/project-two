package com.travelport.projecttwo.services;

import com.travelport.projecttwo.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientEntity> getClients();

    Optional<ClientEntity> getClientById(String id);

    ClientEntity createClient(ClientEntity client);

    Optional<ClientEntity> updateClient(String id, ClientEntity client);

    void deleteClient(String id) throws IllegalArgumentException, NullPointerException;
}

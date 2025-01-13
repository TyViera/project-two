package com.travelport.projecttwo.repository;

import com.travelport.projecttwo.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    List<ClientEntity> getClients();

    Optional<ClientEntity> getClientById(String id);

    void createClient(ClientEntity client);

    Optional<ClientEntity> updateClient(String id, ClientEntity client);

    int deleteClient(String id);

    int getAssociatedSalesCount(String id);
}

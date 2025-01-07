package com.travelport.projecttwo.services;


import com.travelport.projecttwo.services.domainModels.ClientDomain;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    List<ClientDomain> getAllClients();

    Optional<ClientDomain> getClientById(String id);

    ClientDomain createClient(ClientDomain clientDomain);

    void updateClient(ClientDomain clientDomain);

    void deleteClient(String id);
}

package com.travelport.projecttwo.services;


import com.travelport.projecttwo.services.domainModels.ClientDomain;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    List<ClientDomain> getClients();

    Optional<ClientDomain> getClientById(String id);

    ClientDomain createClient(ClientDomain clientDomain);

    ClientDomain updateClient(String id, ClientDomain clientDomain);

    void deleteClient(String id);
}

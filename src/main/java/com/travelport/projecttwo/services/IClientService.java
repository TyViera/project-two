package com.travelport.projecttwo.services;


import com.travelport.projecttwo.services.domainModels.ClientDomain;

import java.util.List;

public interface IClientService {

    List<ClientDomain> getAllClients();
}

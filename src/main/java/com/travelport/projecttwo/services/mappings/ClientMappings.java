package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.services.domainModels.ClientDomain;

public class ClientMappings {
    
    public static ClientDomain toDomain(ClientEntity entity) {
        var client = new ClientDomain();

        client.setNif(entity.getNif());
        client.setName(entity.getName());
        client.setAddress(entity.getAddress());

        return client;
    }
}

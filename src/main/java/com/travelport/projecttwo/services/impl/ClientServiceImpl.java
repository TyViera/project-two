package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.repository.IClientRepository;
import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.services.IClientService;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import com.travelport.projecttwo.services.mappings.ClientMappings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDomain> getAllClients() {
        List<ClientEntity> clientEntityList = clientRepository.findAll();
        List<ClientDomain> clientDomainList = new ArrayList<>(List.of());

        clientEntityList.forEach(clientEntity -> {
            var clientDomain = ClientMappings.toDomain(clientEntity);
            clientDomainList.add(clientDomain);
        });

        return clientDomainList;
    }

    @Override
    public Optional<ClientDomain> getClientById(String id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(ClientMappings::toDomain);
    }

    @Override
    public ClientDomain createClient(ClientDomain clientDomain) {
        clientRepository.save(ClientMappings.toEntity(clientDomain));
        return clientDomain;
    }

    @Override
    public void updateClient(ClientDomain clientDomain) {
        clientRepository.save(ClientMappings.toEntity(clientDomain));
    }
}

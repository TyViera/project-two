package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.jpa.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(UUID id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(UUID id, Client clientDetails) {
        Client client = getClientById(id);
        client.setName(clientDetails.getName());
        client.setNif(clientDetails.getNif());
        client.setAddress(clientDetails.getAddress());
        return clientRepository.save(client);
    }

    public void deleteClient(UUID id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }
}

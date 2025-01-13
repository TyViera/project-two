package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, SaleRepository saleRepository) {
        this.clientRepository = clientRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(String id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) throw new EntityNotFoundException("Client not found");
        return client.get();
    }

    @Override
    public void deleteClient(String id) {
        if(clientRepository.findById(id).isEmpty()) throw new EntityNotFoundException("Client not found");
        if(!saleRepository.getSalesIdByClientId(id).isEmpty()) throw new IllegalArgumentException("Client has orders in system");
        clientRepository.deleteById(id);
    }

    @Override
    public Client addClient(Client client) {
        Client newClient = new Client(UUID.randomUUID().toString() , client.getNif(), client.getName(), client.getAddress());
        return clientRepository.save(newClient);
    }

    @Override
    public Client updateClient(String id, Client clientData) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        existingClient.setName(clientData.getName());
        existingClient.setAddress(clientData.getAddress());

        return clientRepository.save(existingClient);
    }
}

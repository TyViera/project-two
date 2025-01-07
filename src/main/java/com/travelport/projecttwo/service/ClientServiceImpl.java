package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired //TODO: check if this is always necesary
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(String id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) throw new NoSuchElementException("Client not found");
        return client.get();
    }

    @Override
    public void deleteClient(String id) { // TODO: check if i can return an error - when should errors be generated?
        clientRepository.deleteById(id);
    }

    @Override
    public Client addClient(Client client) {
        Client newClient = new Client();
        newClient.setId(UUID.randomUUID().toString());
        newClient.setName(client.getName());
        newClient.setAddress(client.getAddress());
        newClient.setNif(client.getNif());
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

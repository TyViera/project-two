package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Client;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import com.travelport.projecttwo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(String id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
        existingClient.setName(client.getName());
        existingClient.setNif(client.getNif());
        existingClient.setAddress(client.getAddress());
        return clientRepository.save(existingClient);
    }

    public void deleteClient(String id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with ID: " + id);
        }
        clientRepository.deleteById(id);
    }
}

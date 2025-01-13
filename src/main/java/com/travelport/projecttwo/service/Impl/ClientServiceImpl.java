package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClient(String id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(String id, Client client) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            return clientRepository.save(client);
        }
        return null;
    }
}

package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.jpa.ClientJpaRepository;
import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientServiceImpl  implements ClientService {

    @Autowired
    private final ClientJpaRepository clientDao;
    public ClientServiceImpl(ClientJpaRepository clientDao) { this.clientDao = clientDao; }

    @Override
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    @Override
    public Optional<Client> getClientByID(UUID id) {
        var searchedClient = clientDao.findById(id);
        return searchedClient;
    }

    @Override
    public Client createClient(Client client) {
        Client savedClient = clientDao.save(client);
        return savedClient;
    }

    @Override
    public Optional<Client> updateClient(UUID id, Client client) {
        var Client = clientDao.findById(id);
        if (Client.isEmpty()) {
            return Optional.empty();
        }
        var clientUpdate = Client.get();
        if (client.getName() == null || client.getNif() == null) {
            return Optional.empty();
        } else {
            clientUpdate.setName(client.getName());
            clientUpdate.setNif(client.getNif());
            clientUpdate.setAddress(client.getAddress());
        }
        var clientUpdated = clientDao.save(clientUpdate);
        return Optional.of(clientUpdated);
    }

    @Override
    public void deleteClient(UUID id) {
        var coincidentClient = clientDao.existsById(id);
        if (coincidentClient){
            clientDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client doesn't exists");
        }
    }
}

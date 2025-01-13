package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.repository.ClientDao;
import com.travelport.projecttwo.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public List<ClientEntity> getClients() {
        return clientDao.getClients();
    }

    @Override
    public Optional<ClientEntity> getClientById(String id) {
        return clientDao.getClientById(id);
    }

    @Override
    public ClientEntity createClient(ClientEntity client) {
        clientDao.createClient(client);
        return client;
    }

    @Override
    public Optional<ClientEntity> updateClient(String id, ClientEntity client) {
        return clientDao.updateClient(id, client);
    }

    @Override
    public void deleteClient(String id) throws IllegalArgumentException, NullPointerException {
        int salesCount = clientDao.getAssociatedSalesCount(id);
        if (salesCount > 0) {
            throw new IllegalStateException("Client cannot be deleted as it has associated sales.");
        }
        int deletedRows = clientDao.deleteClient(id);
        if (deletedRows == 0) {
            throw new NullPointerException("Client not found");
        }
    }
}

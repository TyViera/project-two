package com.travelport.projecttwo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public Client create(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public Client read(UUID id) {
    return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
  }

  @Override
  public Client update(UUID id, Client client) {
    Client existingClient = read(id);
    existingClient.setName(client.getName());
    existingClient.setAddress(client.getAddress());
    return clientRepository.save(existingClient);
  }

  @Override
  public void delete(UUID id) {
    clientRepository.deleteById(id);
  }

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }
}

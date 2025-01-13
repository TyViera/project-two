package com.travelport.projecttwo.service;


import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private SaleRepository saleRepository;

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  public Optional<Client> getClientById(String id) {
    return clientRepository.findById(id);
  }

  public Client createClient(Client client) {
    return clientRepository.save(client);
  }

  @Transactional
  public Client updateClient(String id, Client updatedClient) {
    Optional<Client> existingClient = getClientById(id);
    if (existingClient.isEmpty()) {
      return null;
    }
    else {
      Client client = existingClient.get();
      client.setName(updatedClient.getName());
      client.setNif(updatedClient.getNif());
      client.setAddress(updatedClient.getAddress());
      return clientRepository.save(client);
    }
  }

  public boolean deleteClient(String id) {
    Optional<Client> client = getClientById(id);
    if (client.isEmpty() || !saleRepository.findSalesByClientId(id).isEmpty()) {
      return false;
    }
    clientRepository.deleteById(id);
    return true;
  }

  public List<Sale> getSalesByClientId(String id) {
    return saleRepository.findSalesByClientId(id);
  }
}

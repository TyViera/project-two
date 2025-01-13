package com.travelport.projecttwo.services;

import java.util.List;
import java.util.UUID;

import com.travelport.projecttwo.entities.Client;

public interface ClientService {
  Client create(Client client);
  Client read(UUID id);
  Client update(UUID id, Client client);
  void delete(UUID id);
  List<Client> getAllClients();
}

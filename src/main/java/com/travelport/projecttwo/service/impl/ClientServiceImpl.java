package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.ClientRequest;
import com.travelport.projecttwo.exception.DeletingClientException;
import com.travelport.projecttwo.repository.entity.ClientEntity;
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
  public List<ClientEntity> getAll () {
    return clientRepository.findAllByOrderByName();
  }

  @Override
  public ClientEntity save (ClientRequest inputClient) {
    var client = new ClientEntity(inputClient);

    return clientRepository.save(client);
  }

  @Override
  public Optional<ClientEntity> getById (String id) {
    return clientRepository.findById(id);
  }

  @Override
  public Optional<ClientEntity> updateById (String id, ClientRequest inputClient) {
    var foundClient = clientRepository.findById(id);
    if (foundClient.isEmpty()) return Optional.empty();

    var updatedClient = foundClient.get();
    updatedClient.setName(inputClient.getName());
    updatedClient.setNif(inputClient.getNif());
    if (inputClient.getAddress() != null) updatedClient.setAddress(inputClient.getAddress());

    return Optional.of(clientRepository.save(updatedClient));
  }

  @Override
  public boolean deleteById (String id) throws DeletingClientException {
    var isClientExist = clientRepository.existsById(id);
    if (!isClientExist) return false;

    // TODO: Refactor to check manually if client has orders, instead of relying on exception
    try {
      clientRepository.deleteById(id);
      return true;
    } catch (Exception e) {
      throw new DeletingClientException("Client has orders, cannot be deleted");
    }
  }

}

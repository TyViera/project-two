package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.ClientRequest;
import com.travelport.projecttwo.exception.DeletingClientException;
import com.travelport.projecttwo.repository.entity.ClientEntity;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.service.SaleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final SaleService salesService;

  public ClientServiceImpl(ClientRepository clientRepository, SaleService salesService) {
    this.clientRepository = clientRepository;
    this.salesService = salesService;
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

    if (!salesService.getSalesByClientId(id).isEmpty()) throw new DeletingClientException("Client has orders, cannot be deleted");

    clientRepository.deleteById(id);
    return true;

  }

}

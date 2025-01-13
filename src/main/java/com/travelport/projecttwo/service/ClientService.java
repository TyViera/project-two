package com.travelport.projecttwo.service;

import com.travelport.projecttwo.controller.model.ClientRequest;
import com.travelport.projecttwo.exception.DeletingClientException;
import com.travelport.projecttwo.repository.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

  public List<ClientEntity> getAll ();

  public ClientEntity save (ClientRequest inputClient);

  public Optional<ClientEntity> getById (String id);

  public Optional<ClientEntity> updateById (String id, ClientRequest inputClient);

  public boolean deleteById (String id) throws DeletingClientException;

}

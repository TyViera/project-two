package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.ClientRequest;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class ClientServiceImplTest {

  @InjectMocks
  private ClientServiceImpl clientService;

  @Mock
  private ClientRepository clientRepository;

  @Test
  @DisplayName("Given empty database, when getAll, then return empty list")
  void testGetAll_emptyDatabase_ReturnEmptyList() {
    var result = clientService.getAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());

    Mockito.verify(clientRepository).findAllByOrderByName();
  }

  @Test
  @DisplayName("Given 2 client exists, when getAll, then return size 2 list")
  void testGetAll_twoClientExists_ReturnList() {
    var client1 = Mockito.mock(ClientEntity.class);
    var client2 = Mockito.mock(ClientEntity.class);
    var clientList = List.of(client1, client2);

    Mockito.when(clientRepository.findAllByOrderByName()).thenReturn(clientList);

    var result = clientService.getAll();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());

    Mockito.verify(clientRepository).findAllByOrderByName();
  }

  @Test
  @DisplayName("Given new client, when save, then return created client")
  void testSave_returnCreatedClient() {
    // Given
    var clientRequest = new ClientRequest();
    clientRequest.setName("client name");
    clientRequest.setNif("10000000D");
    clientRequest.setAddress("client address");

    var clientId = UUID.randomUUID().toString();
    var clientEntity = new ClientEntity(clientRequest);
    clientEntity.setId(clientId);

    Mockito.when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

    // When
    var result = clientService.save(clientRequest);

    // Then
    assertNotNull(result);
    assertEquals(clientEntity, result);
    assertEquals(clientRequest.getName(), result.getName());
    assertEquals(clientRequest.getNif(), result.getNif());
    assertEquals(clientRequest.getAddress(), result.getAddress());

    Mockito.verify(clientRepository).save(any(ClientEntity.class));
  }

  @Test
  @DisplayName("Given client not exists, when getById, then return empty")
  void testGetById_clientNotExists_returnEmpty() {
    var id = UUID.randomUUID().toString();

    var result = clientService.getById(id);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    Mockito.verify(clientRepository).findById(eq(id));
  }

  @Test
  @DisplayName("Given client exists, when getById, then return client")
  void testGetById_clientExists_returnClient() {
    var id = UUID.randomUUID().toString();
    var client = new ClientEntity();
    client.setId(id);

    Mockito.when(clientRepository.findById(eq(id))).thenReturn(Optional.of(client));

    var result = clientService.getById(id);

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(client, result.get());

    Mockito.verify(clientRepository).findById(eq(id));
  }

  @Test
  @DisplayName("Given client not exists, when updateById, then return empty")
  void testUpdateById_clientNotExists_returnEmpty() {
    var id = UUID.randomUUID().toString();
    var clientRequest = new ClientRequest();
    clientRequest.setName("new name");

    Mockito.when(clientRepository.findById(eq(id))).thenReturn(Optional.empty());

    var result = clientService.updateById(id, clientRequest);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    Mockito.verify(clientRepository).findById(eq(id));
    Mockito.verify(clientRepository, Mockito.never()).save(any(ClientEntity.class));
  }

  @Test
  @DisplayName("Given client exists, when updateById, then return updated client")
  void testUpdateById_clientExists_returnUpdatedClient() {
    var id = UUID.randomUUID().toString();
    var client = new ClientEntity();
    client.setId(id);
    client.setName("old name");

    var clientRequest = new ClientRequest();
    clientRequest.setName("new name");

    var updatedClient = new ClientEntity(clientRequest);
    updatedClient.setId(id);

    Mockito.when(clientRepository.findById(eq(id))).thenReturn(Optional.of(client));
    Mockito.when(clientRepository.save(eq(updatedClient))).thenReturn(updatedClient);

    var result = clientService.updateById(id, clientRequest);

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(updatedClient, result.get());

    Mockito.verify(clientRepository).findById(eq(id));
    Mockito.verify(clientRepository).save(eq(updatedClient));
  }

  @Test
  @DisplayName("Given client not exists, when deleteById, then return false")
  void testDeleteById_clientNotExists_returnFalse() {
    var id = UUID.randomUUID().toString();

    Mockito.when(clientRepository.existsById(eq(id))).thenReturn(false);

    var result = clientService.deleteById(id);

    assertFalse(result);

    Mockito.verify(clientRepository).existsById(eq(id));
    Mockito.verify(clientRepository, Mockito.never()).deleteById(eq(id));
  }

  @Test
  @DisplayName("Given client exists, when deleteById, then return true")
  void testDeleteById_clientExists_returnTrue() {
    var id = UUID.randomUUID().toString();

    Mockito.when(clientRepository.existsById(eq(id))).thenReturn(true);

    var result = clientService.deleteById(id);

    assertTrue(result);

    Mockito.verify(clientRepository).existsById(eq(id));
    Mockito.verify(clientRepository).deleteById(eq(id));
  }
}
package com.travelport.projecttwo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

  @InjectMocks
  private ClientServiceImpl clientService;

  @Mock
  private ClientRepository clientRepository;

  private Client client1;
  private Client client2;
  private UUID client1Id;
  private UUID client2Id;

  @BeforeEach
  void setUp() {
    UUID client1Id = UUID.fromString("12345678-1234-1234-1234-123456789A01");
    UUID client2Id = UUID.fromString("98765432-9876-9876-9876-987654321B01");

    client1 = new Client();
    client1.setId(client1Id);
    client1.setName("Aelin Galathynius");
    client1.setAddress("Terrasen");

    client2 = new Client();
    client2.setId(client2Id);
    client2.setName("Rowan Whitethorn");
    client2.setAddress("Terrasen");
  }

  @Test
  void testCreateClient() {
    when(clientRepository.save(client1)).thenReturn(client1);
    Client createdClient = clientService.create(client1);
    assertEquals(client1, createdClient);
    verify(clientRepository, times(1)).save(client1);
  }

  @Test
  void testGetAllClients() {
    when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));
    List<Client> clients = clientService.getAllClients();
    assertEquals(2, clients.size());
    assertEquals("Aelin Galathynius", clients.get(0).getName());
    assertEquals("Rowan Whitethorn", clients.get(1).getName());
    verify(clientRepository, times(1)).findAll();
  }

  @Test
  void testGetClientById() {
    when(clientRepository.findById(client1Id)).thenReturn(Optional.of(client1));
    Client foundClient = clientService.read(client1Id);
    assertEquals("Aelin Galathynius", foundClient.getName());
    assertEquals("Terrasen", foundClient.getAddress());
    verify(clientRepository, times(1)).findById(client1Id);
  }

  @Test
  void testGetClientByIdNotFound() {
    UUID invalidId = UUID.randomUUID();
    when(clientRepository.findById(invalidId)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      clientService.read(invalidId);
    });
    assertEquals("Client not found", exception.getMessage());
    verify(clientRepository, times(1)).findById(invalidId);
  }

  @Test
  void testUpdateClient() {
    Client updatedClient = new Client();
    updatedClient.setName("Aelin Starborn");
    updatedClient.setAddress("Terrasen, Northern Kingdom");

    when(clientRepository.findById(client1Id)).thenReturn(Optional.of(client1));
    when(clientRepository.save(client1)).thenReturn(client1);

    Client updated = clientService.update(client1Id, updatedClient);
    assertEquals("Aelin Starborn", updated.getName());
    assertEquals("Terrasen, Northern Kingdom", updated.getAddress());
    verify(clientRepository, times(1)).findById(client1Id);
    verify(clientRepository, times(1)).save(client1);
  }

  @Test
  void testUpdateClientNotFound() {
    Client updatedClient = new Client();
    updatedClient.setName("Aelin Starborn");
    updatedClient.setAddress("Terrasen, Northern Kingdom");

    UUID invalidId = UUID.randomUUID();
    when(clientRepository.findById(invalidId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      clientService.update(invalidId, updatedClient);
    });
    assertEquals("Client not found", exception.getMessage());
    verify(clientRepository, times(1)).findById(invalidId);
  }

  @Test
  void testDeleteClient() {
    doNothing().when(clientRepository).deleteById(client1Id);
    assertDoesNotThrow(() -> clientService.delete(client1Id));
    verify(clientRepository, times(1)).deleteById(client1Id);
  }
}

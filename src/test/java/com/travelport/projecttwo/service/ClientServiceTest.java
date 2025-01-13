package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Client;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import com.travelport.projecttwo.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClientsTest() {

    }

    @Test
    void getClientByIdTest() {
        String clientId = "1";
        Client mockClient = new Client();
        mockClient.setId(clientId);
        mockClient.setName("Client1");
        mockClient.setNif("123456789");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        Optional<Client> client = clientService.getClientById(clientId);

        assertTrue(client.isPresent());
        assertEquals("Client1", client.get().getName());
    }

    @Test
    void createClientTest() {
        Client client = new Client();
        client.setName("Client1");
        client.setNif("123456789");

        when(clientRepository.save(client)).thenReturn(client);

        Client createdClient = clientService.createClient(client);

        assertNotNull(createdClient);
        assertEquals("Client1", createdClient.getName());
    }

    @Test
    void updateClientTest() {
        String clientId = "1";
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setName("Client1");
        existingClient.setNif("123456789");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));

        Client updatedClient = new Client();
        updatedClient.setName("Updated Name");

        Client result = clientService.updateClient(clientId, updatedClient);

        assertEquals("Updated Name", result.getName());
    }

    @Test
    void deleteClientTest() {
        String clientId = "1";
        when(clientRepository.existsById(clientId)).thenReturn(true);

        clientService.deleteClient(clientId);

        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void deleteClientNotFoundTest() {
        String clientId = "1";
        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(clientId));
    }
}

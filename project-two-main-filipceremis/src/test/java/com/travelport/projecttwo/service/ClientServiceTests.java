package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.jpa.ClientRepository;
import com.travelport.projecttwo.service.impl.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientServiceTests {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(UUID.randomUUID());
        client.setName("JohnDoe");
        client.setNif("123456789");
        client.setAddress("123 Main St");
    }

    @Test
    public void testCreateClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client createdClient = clientService.createClient(client);

        assertNotNull(createdClient);
        assertEquals(client.getName(), createdClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testGetClientById() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        Client foundClient = clientService.getClientById(client.getId());

        assertNotNull(foundClient);
        assertEquals(client.getName(), foundClient.getName());
        verify(clientRepository, times(1)).findById(client.getId());
    }

    @Test
    public void testUpdateClient() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        client.setName("JaneDoe");
        Client updatedClient = clientService.updateClient(client.getId(), client);

        assertNotNull(updatedClient);
        assertEquals("JaneDoe", updatedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void testDeleteClient() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        clientService.deleteClient(client.getId());

        verify(clientRepository, times(1)).delete(client);
    }
}

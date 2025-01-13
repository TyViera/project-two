package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId("1");
        client.setName("John Doe");
        client.setNif("123456789");
        client.setAddress("123 Main St");
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        var clients = clientService.getAllClients();

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
    }

    @Test
    void getClientById() {
        when(clientRepository.findById("1")).thenReturn(Optional.of(client));

        var foundClient = clientService.getClientById("1");

        assertTrue(foundClient.isPresent());
        assertEquals("John Doe", foundClient.get().getName());
    }

    @Test
    void deleteClient() {
        when(clientRepository.existsById("1")).thenReturn(true);

        clientService.deleteClient("1");

        verify(clientRepository, times(1)).deleteById("1");
    }

    @Test
    void save() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        var savedClient = clientService.save(client);

        assertNotNull(savedClient);
        assertEquals("John Doe", savedClient.getName());
    }

    @Test
    void update() {
        when(clientRepository.existsById("1")).thenReturn(true);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        var updatedClient = clientService.update("1", client);

        assertNotNull(updatedClient);
        assertEquals("John Doe", updatedClient.getName());
    }

    @Test
    void updateClientNotFound() {
        when(clientRepository.existsById("1")).thenReturn(false);

        var updatedClient = clientService.update("1", client);

        assertNull(updatedClient);
    }
}
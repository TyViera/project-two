package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private List<Client> clients;
    private List<Sale> sales;

    private final Client client1 = new Client(UUID.randomUUID().toString(), "1231231S", "Pedro Sanchez", "Moncloa 1o 1a, España");
    private final Client client2 = new Client(UUID.randomUUID().toString(), "1231231A", "Sancho Panza", "La Mancha 3, 6o 4a, España");

    @BeforeEach
    public void setUp() {
        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        sales = new ArrayList<>();
        Sale sale = new Sale(UUID.randomUUID().toString(), client1);
        sales.add(sale);
    }

    @Test
    void getClients() {
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getClients();

        assertNotNull(result);
        assertEquals(2, result.size());

        Client resultClient1 = result.get(0);
        Client resultClient2 = result.get(1);

        assertEquals(client1.getId(), resultClient1.getId());
        assertEquals(client1.getName(), resultClient1.getName());
        assertEquals(client1.getAddress(), resultClient1.getAddress());

        assertEquals(client2.getId(), resultClient2.getId());
        assertEquals(client2.getName(), resultClient2.getName());
        assertEquals(client2.getAddress(), resultClient2.getAddress());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById() {
        String clientId = client1.getId();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client1));

        Client result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(client1.getId(), result.getId());
        assertEquals(client1.getName(), result.getName());
        assertEquals(client1.getAddress(), result.getAddress());

        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void getClientById_NotFound() {
        String clientId = UUID.randomUUID().toString();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(clientId));
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void deleteClient() {
        String clientId = client1.getId();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client1));
        when(saleRepository.getSalesIdByClientId(clientId)).thenReturn(List.of());

        assertDoesNotThrow(() -> clientService.deleteClient(clientId));

        verify(clientRepository, times(1)).findById(clientId);
        verify(saleRepository, times(1)).getSalesIdByClientId(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void deleteClient_HasSales() {
        String clientId = client1.getId();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client1));
        when(saleRepository.getSalesIdByClientId(clientId)).thenReturn(List.of("sale1"));

        assertThrows(IllegalArgumentException.class, () -> clientService.deleteClient(clientId));

        verify(clientRepository, times(1)).findById(clientId);
        verify(saleRepository, times(1)).getSalesIdByClientId(clientId);
        verify(clientRepository, never()).deleteById(clientId);
    }

    @Test
    void deleteClient_NotFound() {
        String clientId = UUID.randomUUID().toString();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.deleteClient(clientId));

        verify(clientRepository, times(1)).findById(clientId);
        verify(saleRepository, never()).getSalesIdByClientId(clientId);
    }

    @Test
    void addClient() {
        Client newClient = new Client(null, "55555555C", "Sancho Panza", "La Mancha, España");
        Client savedClient = new Client(UUID.randomUUID().toString(), "55555555C", "Sancho Panza", "La Mancha, España");
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = clientService.addClient(newClient);

        assertNotNull(result);
        assertEquals(savedClient.getName(), result.getName());
        assertEquals(savedClient.getId(), result.getId());

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient() {
        String clientId = client1.getId();
        Client updatedClientData = new Client(null, "12345678A", "Pedro Almodovar", "Madrid, España");
        Client updatedClient = new Client(clientId, "12345678A", "Pedro Almodovar", "Madrid, España");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client1));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = clientService.updateClient(clientId, updatedClientData);

        assertNotNull(result);
        assertEquals(updatedClient.getId(), result.getId());
        assertEquals(updatedClient.getName(), result.getName());
        assertEquals(updatedClient.getAddress(), result.getAddress());

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient_NotFound() {
        String clientId = UUID.randomUUID().toString();
        Client updatedClientData = new Client(null, "55555555C", "Unknown", "Nowhere");

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.updateClient(clientId, updatedClientData));

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).save(any(Client.class));
    }
}

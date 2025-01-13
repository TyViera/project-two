package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.repository.ClientDao;
import com.travelport.projecttwo.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    void getClientsTest() {
        List<ClientEntity> mockClients = List.of(
                new ClientEntity("1", "John Doe", "123456789", "123 Main St"),
                new ClientEntity("2", "Jane Smith", "987654321", "456 Elm St")
        );

        when(clientDao.getClients()).thenReturn(mockClients);

        List<ClientEntity> result = clientService.getClients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(clientDao).getClients();
    }

    @Test
    void getClientByIdTest() {
        Optional<ClientEntity> mockClient = Optional.of(new ClientEntity("1", "John Doe", "123456789", "123 Main St"));

        when(clientDao.getClientById(eq("1"))).thenReturn(mockClient);

        Optional<ClientEntity> result = clientService.getClientById("1");

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(clientDao).getClientById(eq("1"));
    }

    @Test
    void createClientTest() {
        ClientEntity newClient = new ClientEntity("3", "Ana Lev", "123456789", "789 Oak St");

        doNothing().when(clientDao).createClient(newClient);

        ClientEntity result = clientService.createClient(newClient);

        assertNotNull(result);
        assertEquals("Ana Lev", result.getName());
        verify(clientDao).createClient(newClient);
    }

    @Test
    void updateClientTest() {
        ClientEntity updatedClient = new ClientEntity("1", "John Updated", "123456789", "123 Updated St");
        Optional<ClientEntity> mockUpdatedClient = Optional.of(updatedClient);

        when(clientDao.updateClient(eq("1"), eq(updatedClient))).thenReturn(mockUpdatedClient);

        Optional<ClientEntity> result = clientService.updateClient("1", updatedClient);

        assertTrue(result.isPresent());
        assertEquals("John Updated", result.get().getName());
        verify(clientDao).updateClient(eq("1"), eq(updatedClient));
    }

    @Test
    void deleteClientTest_success() {
        when(clientDao.getAssociatedSalesCount(eq("1"))).thenReturn(0);
        when(clientDao.deleteClient(eq("1"))).thenReturn(1);

        assertDoesNotThrow(() -> clientService.deleteClient("1"));

        verify(clientDao).getAssociatedSalesCount(eq("1"));
        verify(clientDao).deleteClient(eq("1"));
    }

    @Test
    void deleteClientTest_withAssociatedSales() {
        when(clientDao.getAssociatedSalesCount(eq("1"))).thenReturn(5);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> clientService.deleteClient("1"));
        assertEquals("Client cannot be deleted as it has associated sales.", exception.getMessage());

        verify(clientDao).getAssociatedSalesCount(eq("1"));
        verify(clientDao, never()).deleteClient(eq("1"));
    }

    @Test
    void deleteClientTest_clientNotFound() {
        when(clientDao.getAssociatedSalesCount(eq("1"))).thenReturn(0);
        when(clientDao.deleteClient(eq("1"))).thenReturn(0);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> clientService.deleteClient("1"));
        assertEquals("Client not found", exception.getMessage());

        verify(clientDao).getAssociatedSalesCount(eq("1"));
        verify(clientDao).deleteClient(eq("1"));
    }
}


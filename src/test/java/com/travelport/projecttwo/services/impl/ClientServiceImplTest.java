package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.repository.embeddables.SalesDetId;
import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.repository.entities.SaleCabEntity;
import com.travelport.projecttwo.repository.entities.SaleDetEntity;
import com.travelport.projecttwo.services.mappings.ClientMappings;
import com.travelport.projecttwo.exceptions.ClientHasSalesException;
import com.travelport.projecttwo.repository.IClientRepository;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.ISalesCabRepository;
import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ISalesCabRepository salesRepository;

    @Mock
    private IProductRepository productRepository;

    @Mock
    ClientMappings clientMappings;

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp(){
        clientService = new ClientServiceImpl(
                clientRepository,
                salesRepository,
                productRepository
        );
    }

    @Test
    void getClients_shouldReturnAllClients() {
        // GIVEN
        var client1 = new ClientEntity();
        client1.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        client1.setNif("12345678A");
        client1.setName("John Doe");
        client1.setAddress("123 Main Street");

        var client2 = new ClientEntity();
        client2.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384318");
        client2.setNif("12345678B");
        client2.setName("Jane Doe");
        client2.setAddress("123 Main Street");

        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));

        // WHEN
        var result = clientService.getClients();

        // THEN
        assertEquals(2, result.size());
        assertEquals("12345678A", result.get(0).getNif());
        assertEquals("12345678B", result.get(1).getNif());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_shouldReturnClientDomainIfExists() {
        // GIVEN
        var client1 = new ClientEntity();
        client1.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        client1.setNif("12345678A");
        client1.setName("John Doe");
        client1.setAddress("123 Main Street");

        when(clientRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.of(client1));

        // WHEN
        var result = clientService.getClientById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");

        // THEN
        assertTrue(result.isPresent());
        assertEquals("12345678A", result.get().getNif());
        assertEquals("123 Main Street", result.get().getAddress());
        verify(clientRepository, times(1)).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void createClient_whenClientNifAlreadyExists_shouldThrowIllegalArgument() {
        // GIVEN
        var clientDomain = new ClientDomain();
        clientDomain.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        clientDomain.setNif("12345678A");
        clientDomain.setName("John Doe");
        clientDomain.setAddress("123 Main Street");

        when(clientRepository.existsByNif("12345678A")).thenReturn(true);

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(clientDomain));
        verify(clientRepository, times(1)).existsByNif("12345678A");
    }

    @Test
    void createClient_whenClientNifDoesntExist_shouldCreate() {
        // GIVEN
        var clientDomain = new ClientDomain();
        clientDomain.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        clientDomain.setNif("12345678A");
        clientDomain.setName("John Doe");
        clientDomain.setAddress("123 Main Street");

        when(clientRepository.existsByNif("12345678A")).thenReturn(false);

        // WHEN
        clientService.createClient(clientDomain);

        // THEN
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void updateClient_whenClientDontExist_shouldThrowIllegalArgument() {
        // GIVEN
        var clientId = "2cba93e4-1eac-4ba7-b8cb-6febf3384319";

        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> clientService.updateClient(clientId, new ClientDomain()));
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void updateClient_whenClientExists_shouldUpdate() {
        // GIVEN
        var clientId = "2cba93e4-1eac-4ba7-b8cb-6febf3384319";
        var clientDomain = new ClientDomain();
        clientDomain.setId(clientId);
        clientDomain.setNif("12345678A");
        clientDomain.setName("John Doe");
        clientDomain.setAddress("123 Main Street");

        var clientEntity = new ClientEntity();
        clientEntity.setId(clientId);
        clientEntity.setNif("12345678A");
        clientEntity.setName("John Doe");
        clientEntity.setAddress("123 Main Street");

        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(clientEntity));
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

        // WHEN
        var result = clientService.updateClient(clientId, clientDomain);

        // THEN
        assertEquals("12345678A", result.getNif());
        assertEquals("123 Main Street", result.getAddress());
        assertEquals("John Doe", result.getName());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void deleteClient_whenClientHasSales_shouldThrowException() {
        // GIVEN
        var client1 = new ClientEntity();
        client1.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        client1.setNif("12345678A");
        client1.setName("John Doe");
        client1.setAddress("123 Main Street");

        when(clientRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.of(client1));
        when(salesRepository.existsByClientId("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(true);

        // WHEN & THEN
        assertThrows(ClientHasSalesException.class, () -> clientService.deleteClient("2cba93e4-1eac-4ba7-b8cb-6febf3384319"));
        verify(salesRepository, times(1)).existsByClientId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void deleteClient_whenClientNotFound_shouldThrowIllegalArgument() {
        // GIVEN
        when(clientRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> clientService.deleteClient("2cba93e4-1eac-4ba7-b8cb-6febf3384319"));
        verify(salesRepository, never()).existsByClientId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        verify(clientRepository, times(1)).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void deleteClient_whenClientExistsAndNoSales_shouldDelete() {
        // GIVEN
        var client1 = new ClientEntity();
        client1.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        client1.setNif("12345678A");
        client1.setName("John Doe");
        client1.setAddress("123 Main Street");

        when(clientRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.of(client1));
        when(salesRepository.existsByClientId("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(false);

        // WHEN
        clientService.deleteClient("2cba93e4-1eac-4ba7-b8cb-6febf3384319");

        // THEN
        verify(clientRepository, times(1)).delete(client1);
    }

    @Test
    void getClientSales_whenClientDontExist_shouldThrowIllegalArgument() {
        // GIVEN
        when(clientRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> clientService.getClientSales("2cba93e4-1eac-4ba7-b8cb-6febf3384319"));
        verify(clientRepository, times(1)).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");

    }

    @Test
    void getClientSales_clientExists_shouldReturnSales() {
        // GIVEN
        var saleId = "2cba93e4-1eac-4ba7-b8cb-6febf3384319";
        var clientId = "2cba93e4-1eac-4ba7-b8cb-6febf3384318";
        var productId = "2cba93e4-1eac-4ba7-b8cb-6febf3384317";

        var clientEntity = new ClientEntity();
        clientEntity.setId(clientId);
        clientEntity.setNif("12345678A");
        clientEntity.setName("John Doe");
        clientEntity.setAddress("123 Main Street");

        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(clientEntity));

        var saleCabEntity = new SaleCabEntity();
        saleCabEntity.setId(saleId);
        saleCabEntity.setClientId(clientId);

        var saleDetEntity = new SaleDetEntity();
        var saleDetId = new SalesDetId();

        saleDetId.setSaleId(saleId);
        saleDetId.setProductId(productId);

        saleDetEntity.setId(saleDetId);
        saleDetEntity.setQuantity(2);
        saleDetEntity.setSaleCab(saleCabEntity);

        saleCabEntity.setDetails(List.of(saleDetEntity));

        when(salesRepository.findAllByClientId(clientId)).thenReturn(List.of(saleCabEntity));

        var productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setName("Laptop XYZ");

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(productEntity));

        // WHEN
        var result = clientService.getClientSales(clientId);

        // THEN
        assertEquals(1, result.size());
        assertEquals("Laptop XYZ", result.getFirst().getProducts().getFirst().getProduct().getName());
        assertEquals(productId, result.getFirst().getProducts().getFirst().getProduct().getId());

        verify(clientRepository, times(1)).findById(clientId);
        verify(salesRepository, times(1)).findAllByClientId(clientId);
        verify(productRepository, times(1)).findById(productId);
    }
}
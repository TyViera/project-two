package com.travelport.projecttwo.persistence;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.repository.impl.ClientDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@JdbcTest
@ExtendWith(MockitoExtension.class)
class ClientDaoImplTest {

    @InjectMocks
    private ClientDaoImpl clientDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void getClientsTest() {
        String query = "SELECT id, name, nif, address FROM clients";
        List<ClientEntity> mockClients = List.of(
                new ClientEntity("1", "John Doe", "123456789", "123 Main St"),
                new ClientEntity("2", "Jane Smith", "987654321", "456 Elm St")
        );

        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class))).thenReturn(mockClients);

        List<ClientEntity> result = clientDao.getClients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());

        Mockito.verify(jdbcTemplate).query(eq(query), any(RowMapper.class));
    }

    @Test
    void getClientByIdTest() {
        String query = "SELECT id, name, nif, address FROM clients WHERE id = ?";
        ClientEntity mockClient = new ClientEntity("1", "John Doe", "123456789", "123 Main St");

        Mockito.when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), eq("1")))
                .thenReturn(Optional.of(mockClient));

        Optional<ClientEntity> result = clientDao.getClientById("1");

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());

        Mockito.verify(jdbcTemplate).queryForObject(eq(query), any(RowMapper.class), eq("1"));
    }

    @Test
    void createClientTest() {
        String insert = "INSERT INTO clients (id, name, nif, address) VALUES (?, ?, ?, ?)";
        ClientEntity newClient = new ClientEntity("3", "Ana Lev", "123456789", "789 Oak St");

        Mockito.when(jdbcTemplate.update(eq(insert), eq("3"), eq("Ana Lev"), eq("123456789"), eq("789 Oak St"))).thenReturn(1);

        clientDao.createClient(newClient);

        Mockito.verify(jdbcTemplate).update(eq(insert), eq("3"), eq("Ana Lev"), eq("123456789"), eq("789 Oak St"));
    }

    @Test
    void updateClientTest() {
        String update = "UPDATE clients SET name = ?, nif = ?, address = ? WHERE id = ?";
        ClientEntity updatedClient = new ClientEntity("1", "John Updated", "123456789", "123 Updated St");

        Mockito.when(jdbcTemplate.update(eq(update), eq("John Updated"), eq("123456789"), eq("123 Updated St"), eq("1"))).thenReturn(1);

        Optional<ClientEntity> result = clientDao.updateClient("1", updatedClient);

        assertTrue(result.isPresent());
        assertEquals("John Updated", result.get().getName());

        Mockito.verify(jdbcTemplate).update(eq(update), eq("John Updated"), eq("123456789"), eq("123 Updated St"), eq("1"));
    }

    @Test
    void deleteClientTest() {
        String deleteSql = "DELETE FROM clients WHERE id = ?";
        Mockito.when(jdbcTemplate.update(eq(deleteSql), eq("1"))).thenReturn(1);

        int result = clientDao.deleteClient("1");

        assertEquals(1, result);
        Mockito.verify(jdbcTemplate).update(eq(deleteSql), eq("1"));
    }

    @Test
    void getAssociatedSalesCountTest() {
        String query = "SELECT COUNT(*) FROM sales WHERE client_id = ?";
        Mockito.when(jdbcTemplate.queryForObject(eq(query), eq(Integer.class), eq("1"))).thenReturn(5);

        int result = clientDao.getAssociatedSalesCount("1");

        assertEquals(5, result);
        Mockito.verify(jdbcTemplate).queryForObject(eq(query), eq(Integer.class), eq("1"));
    }
}
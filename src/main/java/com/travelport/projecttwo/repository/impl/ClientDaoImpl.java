package com.travelport.projecttwo.repository.impl;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.repository.ClientDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClientEntity> getClients() {
        String query = "SELECT id, name, nif, address FROM clients";
        return jdbcTemplate.query(query, (rs, row) -> {
            return new ClientEntity(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("nif"),
                rs.getString("address")
            );
        });
    }

    @Override
    public Optional<ClientEntity> getClientById(String id) {
        String query = "SELECT id, name, nif, address FROM clients WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, row) -> {
                ClientEntity client = new ClientEntity(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("name"),
                    rs.getString("address")
                );
                return Optional.of(client);
            });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public ClientEntity createClient(ClientEntity client) {
        String insert = "INSERT INTO clients (id, name, nif, address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insert, client.getId(), client.getName(), client.getNif(), client.getAddress());
        return client;
    }

    @Override
    public Optional<ClientEntity> updateClient(String id, ClientEntity client) {
        String update = "UPDATE clients SET name = ?, nif = ?, address = ? WHERE id = ?";
        int updateCount = jdbcTemplate.update(update, client.getName(), client.getNif(), client.getAddress(), id);
        if (updateCount == 1) {
            return Optional.of(client);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int deleteClient(String id) {
        String deleteSql = "DELETE FROM clients WHERE id = ?";
        return jdbcTemplate.update(deleteSql, id);
    }

    @Override
    public int getAssociatedSalesCount(String id) {
        String query = "SELECT COUNT(*) FROM sales WHERE client_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
    }
}

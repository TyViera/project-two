package com.travelport.projecttwo.service;

import com.travelport.projecttwo.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Override
    public List<Client> getAll() {
        Client client1 = new Client();
        client1.setId("123e4567-e89b-12d3-a456-426614174000");
        client1.setName("John Doe");
        client1.setNif("123456789");
        client1.setAddress("Rambles n2, 1r 1a");

        Client client2 = new Client();
        client2.setId("223e4567-e89b-12d3-a456-426614174001");
        client2.setName("Jane Smith");
        client2.setNif("987654321");
        client2.setAddress("Avenida de la Paz 5, 3b");

        return List.of(
             client1, client2
        );
    }
}

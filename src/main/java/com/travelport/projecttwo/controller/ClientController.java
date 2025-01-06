package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "clients", description = "The clients API")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }
}

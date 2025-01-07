package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAll() {
       return clientService.getAllClients();
    }

}

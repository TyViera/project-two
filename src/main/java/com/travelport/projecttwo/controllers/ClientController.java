package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.ClientRequestDto;
import com.travelport.projecttwo.controllers.dtos.ClientResponseDto;
import com.travelport.projecttwo.controllers.mappings.ClientMappings;
import com.travelport.projecttwo.services.IClientService;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponseDto> getClients() {
        List<ClientDomain> clientDomainList = clientService.getAllClients();

        return ClientMappings.toDto(clientDomainList);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable String id) {
            var clientDomain = clientService.getClientById(id);

        if (clientDomain.isPresent()) {
            return ResponseEntity.ok(ClientMappings.toDto(clientDomain.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> postClient(@Validated @RequestBody ClientRequestDto clientRequest) {
        var clientDomain = ClientMappings.toDomain(clientRequest);

        clientDomain.setId(UUID.randomUUID().toString());

        var savedClient = clientService.createClient(clientDomain);

        URI location = URI.create("/clients/" + savedClient.getId());

        return ResponseEntity.created(location).body(ClientMappings.toDto(savedClient));
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable String id, @Validated @RequestBody ClientRequestDto clientRequest) {
        var clientDomain = clientService.getClientById(id);
        if (clientDomain.isPresent()) {
            clientDomain.get().setNif(clientRequest.getNif());
            clientDomain.get().setName(clientRequest.getName());
            clientDomain.get().setAddress(clientRequest.getAddress());
            clientService.updateClient(clientDomain.get());
            return ResponseEntity.ok(ClientMappings.toDto(clientDomain.get()));
        }
        return ResponseEntity.notFound().build();
    }
}

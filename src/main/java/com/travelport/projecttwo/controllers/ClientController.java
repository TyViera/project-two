package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.past_sales.ClientPastSalesDto;
import com.travelport.projecttwo.controllers.dtos.client.ClientRequestDto;
import com.travelport.projecttwo.controllers.dtos.client.ClientResponseDto;
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
        List<ClientDomain> clientDomainList = clientService.getClients();

        return ClientMappings.toDto(clientDomainList);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable String id) {
        var clientDomain = clientService.getClientById(id);

        if (clientDomain.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ClientMappings.toDto(clientDomain.get()));
    }

    @GetMapping("{id}/sales")
    public ResponseEntity<List<ClientPastSalesDto>> getClientSales(@PathVariable String id) {
        try {
            var sales = clientService.getClientSales(id);
            return ResponseEntity.ok(sales);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
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
        try{
            var clientDomain = clientService.updateClient(id, ClientMappings.toDomain(clientRequest));
            return ResponseEntity.ok(ClientMappings.toDto(clientDomain));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        // TODO return 422 if client has orders (sales in the system)
        try{
            clientService.deleteClient(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@PreAuthorize("isAuthenticated()")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<List<Client>>getAllClients() {
    return ResponseEntity.ok(clientService.getAllClients());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> getClientById(@PathVariable String id) {
    return clientService.getClientById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
    return ResponseEntity.status(201).body(clientService.createClient(client));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Client> updateClient(@PathVariable String id, @Valid @RequestBody Client client) {
    Client updatedClient = clientService.updateClient(id, client);
    if (updatedClient == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedClient);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable String id) {
    boolean isDeleted = clientService.deleteClient(id);
    if (!isDeleted) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/sales")
  public ResponseEntity<Object> getClientSales(@PathVariable String id) {
    return ResponseEntity.ok(clientService.getSalesByClientId(id));
  }
}
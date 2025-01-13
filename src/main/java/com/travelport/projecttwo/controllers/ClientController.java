package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.services.ClientServiceImpl;
import com.travelport.projecttwo.services.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  private ClientServiceImpl clientService;

  @Autowired
  private SaleService saleService;

  @GetMapping
  public ResponseEntity<List<Client>> getAllClients() {
    List<Client> clients = clientService.getAllClients();
    return new ResponseEntity<>(clients, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
    try {
      Client client = clientService.read(id);
      return new ResponseEntity<>(client, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Client> createClient(@RequestBody Client client) {
    Client createdClient = clientService.create(client);
    return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody Client client) {
    try {
      Client updatedClient = clientService.update(id, client);
      return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
    try {
      boolean hasOrders = saleService.hasSales(id);

      if (hasOrders) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }

      clientService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  @GetMapping("/{id}/sales")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Object>> getClientSales(@PathVariable UUID id) {
    List<Sale> sales = saleService.getPastSales(id);
    List<Object> response = sales.stream()
        .map(sale -> {
          return Map.of(
              "id", sale.getId().toString(),
              "products", sale.getProducts().stream()
                  .map(productSale -> {
                    return Map.of(
                        "product", Map.of(
                            "id", productSale.getProduct().getId().toString(),
                            "name", productSale.getProduct().getName()
                        ),
                        "quantity", productSale.getQuantity()
                    );
                  })
                  .collect(Collectors.toList())
          );
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

}

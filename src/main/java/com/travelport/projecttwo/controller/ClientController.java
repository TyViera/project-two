package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.SaleResponse;
import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.service.SaleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/clients")
public class ClientController {

    // TODO: catch errors from the service layer and transform them into http responses
    private final ClientService clientService;
    private final SaleService saleService;

    public ClientController(ClientService clientService, SaleService saleService) {
        this.clientService = clientService;
        this.saleService=saleService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Object> findById( @Parameter(name = "id", description = "ID of client to return", required = true, in = ParameterIn.PATH) @PathVariable("id") String id){
        Client client;
        try{
            client = clientService.getClientById(id);

        }catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                client,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Object> postClient(@RequestBody Client client){
        try {
            Client newClient=clientService.addClient(client);
            return new ResponseEntity<>(
                    newClient,
                    HttpStatus.CREATED
            );
        }catch(Exception e){
            return new ResponseEntity<>(
                    "Client not saved",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClientById(@PathVariable("id") String id, @RequestBody Client client){
        try {
            Client clientUpdated=clientService.updateClient(id, client);
            return new ResponseEntity<>(
                    clientUpdated,
                    HttpStatus.OK
            );
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable("id") String id){
        try{
            clientService.deleteClient(id);
            return new ResponseEntity<>("Operation successful", HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Sales in the system", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/{id}/sales")
    public ResponseEntity<Object> getClientSales(@PathVariable("id") String id){
        try{
            List<SaleResponse> sales =saleService.getSalesByClientId(id);
            return new ResponseEntity<>(sales, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
